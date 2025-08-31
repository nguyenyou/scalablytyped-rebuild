package org.scalablytyped.converter.internal
package ts

import io.circe.{Decoder, Encoder, KeyDecoder, KeyEncoder}

import scala.util.hashing.MurmurHash3.productHash

/** Base trait for all TypeScript AST (Abstract Syntax Tree) nodes. This
  * represents any element in a TypeScript source file - declarations, types,
  * expressions, etc. Think of this as the foundation for modeling the entire
  * structure of TypeScript code.
  */
sealed trait TsTree extends Serializable with Product {

  override def canEqual(that: Any): Boolean = that.## == ##

  override lazy val hashCode: Int = productHash(this)

  /** Provides a human-readable string representation of this tree node, useful
    * for debugging and logging. Shows the node type and name if available.
    */
  lazy val asString: String = {
    val name = this match {
      case named: TsNamedDecl                               => named.name.value
      case TsMemberProperty(_, _, TsIdent(str), _, _, _, _) => str
      case TsMemberFunction(_, _, TsIdent(str), _, _, _, _) => str
      case _                                                => ""
    }
    s"${getClass.getSimpleName}($name)"
  }
}

/** Represents TypeScript elements that can either be containers (like
  * namespaces, modules) or declarations (like classes, interfaces, functions).
  * This is the union of things that can appear at the top level of a TypeScript
  * file.
  */
sealed trait TsContainerOrDecl extends TsTree

/** Represents TypeScript declarations - things that introduce new names into
  * scope. Examples: class declarations, interface declarations, function
  * declarations, variable declarations. In TypeScript: `class MyClass {}`,
  * `interface MyInterface {}`, `function myFunc() {}`, etc.
  */
sealed trait TsDecl extends TsContainerOrDecl

/** Represents TypeScript containers that can hold other declarations or
  * containers. Examples: namespaces, modules, classes, interfaces. In
  * TypeScript: `namespace MyNamespace { ... }`, `module "my-module" { ... }`
  */
sealed trait TsContainer
    extends TsContainerOrDecl
    with MemberCache
    with CodePath.Has {

  /** The declarations and containers nested within this container */
  def members: IArray[TsContainerOrDecl]

  /** Creates a copy of this container with new members */
  def withMembers(newMembers: IArray[TsContainerOrDecl]): TsContainer
}

/** Represents TypeScript declarations that have a name. Examples: named
  * classes, interfaces, functions, variables, type aliases. In TypeScript:
  * `class MyClass`, `interface IMyInterface`, `function myFunction`, etc.
  */
sealed trait TsNamedDecl extends TsDecl with CodePath.Has {

  /** JSDoc comments and other documentation associated with this declaration */
  val comments: Comments
  def withComments(cs: Comments): TsNamedDecl
  final def addComment(c: Comment) = withComments(comments + c)

  /** The identifier/name of this declaration */
  def name: TsIdent
  def withName(name: TsIdentSimple): TsNamedDecl
}

/** Represents TypeScript declarations that introduce values (not just types)
  * into scope. Examples: classes, enums, functions, variables. In TypeScript:
  * `class MyClass` (creates both type and value), `const myVar = 5`, `function
  * myFunc() {}` Note: interfaces and type aliases are NOT value declarations -
  * they only exist at compile time.
  */
sealed trait TsNamedValueDecl extends TsNamedDecl

/** Represents a complete TypeScript source file after parsing. This is the root
  * node of the AST for a single .ts or .d.ts file. In TypeScript: the entire
  * content of a file like "myFile.ts"
  */
final case class TsParsedFile(
    /** JSDoc comments at the file level */
    comments: Comments,
    /** Compiler directives like /// <reference types="node" /> */
    directives: IArray[Directive],
    /** All top-level declarations in this file (classes, interfaces, functions,
      * etc.)
      */
    members: IArray[TsContainerOrDecl],
    /** Path information for this file within the project structure */
    codePath: CodePath
) extends TsContainer {

  /** Checks if this file represents TypeScript's standard library definitions.
    * Standard library files contain built-in types like Array, Object, etc.
    */
  lazy val isStdLib: Boolean =
    directives.exists {
      case Directive.NoStdLib => true
      case _                  => false
    }

  override def withMembers(
      newMembers: IArray[TsContainerOrDecl]
  ): TsParsedFile =
    copy(members = newMembers)

  override def withCodePath(newCodePath: CodePath): CodePath.Has =
    copy(codePath = newCodePath)
}

/** Base trait for TypeScript namespace and module declarations. Both namespaces
  * and modules create named scopes that can contain other declarations.
  */
sealed trait TsDeclNamespaceOrModule
    extends TsContainer
    with TsNamedValueDecl
    with JsLocation.Has

/** Base trait for module-like declarations (modules and augmented modules).
  * These represent different ways of declaring modules in TypeScript.
  */
sealed trait TsDeclModuleLike extends TsDeclNamespaceOrModule

/** Represents a TypeScript namespace declaration. In TypeScript: `namespace
  * MyNamespace { ... }` or `declare namespace MyNamespace { ... }` Namespaces
  * group related functionality and prevent naming conflicts.
  */
final case class TsDeclNamespace(
    /** JSDoc comments for this namespace */
    comments: Comments,
    /** Whether this is a declare namespace (ambient declaration) */
    declared: Boolean,
    /** The name of the namespace */
    name: TsIdentSimple,
    /** All declarations contained within this namespace */
    members: IArray[TsContainerOrDecl],
    /** Path information for this namespace */
    codePath: CodePath,
    /** Location in the JavaScript output where this namespace will be placed */
    jsLocation: JsLocation
) extends TsDeclNamespaceOrModule
    with TsNamedDecl {

  override def withCodePath(newCodePath: CodePath): TsDeclNamespace =
    copy(codePath = newCodePath)

  override def withMembers(
      newMembers: IArray[TsContainerOrDecl]
  ): TsDeclNamespace =
    copy(members = newMembers)

  override def withJsLocation(newLocation: JsLocation): TsDeclNamespace =
    copy(jsLocation = newLocation)

  override def withName(newName: TsIdentSimple): TsDeclNamespace =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclNamespace =
    copy(comments = cs)
}

/** Represents a TypeScript module declaration. In TypeScript: `module
  * "my-module" { ... }` or `declare module "my-module" { ... }` Modules are
  * used to declare the shape of external modules or to augment existing ones.
  */
final case class TsDeclModule(
    /** JSDoc comments for this module */
    comments: Comments,
    /** Whether this is a declare module (ambient declaration) */
    declared: Boolean,
    /** The module name/path (e.g., "lodash", "@types/node") */
    name: TsIdentModule,
    /** All declarations contained within this module */
    members: IArray[TsContainerOrDecl],
    /** Path information for this module */
    codePath: CodePath,
    /** Location in the JavaScript output where this module will be placed */
    jsLocation: JsLocation
) extends TsDeclModuleLike {

  override def withMembers(
      newMembers: IArray[TsContainerOrDecl]
  ): TsDeclModule =
    copy(members = newMembers)

  override def withCodePath(newCodePath: CodePath): TsDeclModule =
    copy(codePath = newCodePath)

  override def withJsLocation(newLocation: JsLocation): TsDeclModule =
    copy(jsLocation = newLocation)

  override def withName(name: TsIdentSimple): TsDeclNamespace =
    TsDeclNamespace(
      comments,
      declared = false,
      name,
      members,
      codePath,
      jsLocation
    )

  override def withComments(cs: Comments): TsDeclModule =
    copy(comments = cs)
}

/** Represents a TypeScript module augmentation. In TypeScript: `declare module
  * "existing-module" { ... }` when adding to an existing module Used to extend
  * or modify the type definitions of existing modules.
  */
final case class TsAugmentedModule(
    /** JSDoc comments for this module augmentation */
    comments: Comments,
    /** The name of the module being augmented */
    name: TsIdentModule,
    /** Additional declarations being added to the existing module */
    members: IArray[TsContainerOrDecl],
    /** Path information for this augmentation */
    codePath: CodePath,
    /** Location in the JavaScript output where this augmentation will be placed
      */
    jsLocation: JsLocation
) extends TsDeclModuleLike {
  override def withMembers(
      newMembers: IArray[TsContainerOrDecl]
  ): TsAugmentedModule =
    copy(members = newMembers)

  override def withCodePath(newCodePath: CodePath): TsAugmentedModule =
    copy(codePath = newCodePath)

  override def withJsLocation(newLocation: JsLocation): TsAugmentedModule =
    copy(jsLocation = newLocation)

  override def withName(name: TsIdentSimple): TsDeclNamespace =
    TsDeclNamespace(
      NoComments,
      declared = false,
      name,
      members,
      codePath,
      jsLocation
    )

  override def withComments(cs: Comments): TsAugmentedModule =
    copy(comments = cs)
}

/** Represents a TypeScript global scope declaration. In TypeScript: `declare
  * global { ... }` Used to add declarations to the global scope, typically in
  * module files.
  */
final case class TsGlobal(
    /** JSDoc comments for this global declaration */
    comments: Comments,
    /** Whether this is a declare global (ambient declaration) */
    declared: Boolean,
    /** All declarations being added to the global scope */
    members: IArray[TsContainerOrDecl],
    /** Path information for this global declaration */
    codePath: CodePath
) extends TsContainer
    with CodePath.Has {
  override def withMembers(newMembers: IArray[TsContainerOrDecl]): TsGlobal =
    copy(members = newMembers)

  override def withCodePath(newCodePath: CodePath): CodePath.Has =
    copy(codePath = newCodePath)
}

/** Represents a TypeScript class declaration. In TypeScript: `class MyClass
  * extends BaseClass implements IInterface { ... }` Classes create both a type
  * (for type checking) and a value (the constructor function).
  */
final case class TsDeclClass(
    /** JSDoc comments for this class */
    comments: Comments,
    /** Whether this is a declare class (ambient declaration) */
    declared: Boolean,
    /** Whether this class is abstract (cannot be instantiated directly) */
    isAbstract: Boolean,
    /** The name of the class */
    name: TsIdentSimple,
    /** Generic type parameters (e.g., <T, U> in class MyClass<T, U>) */
    tparams: IArray[TsTypeParam],
    /** The parent class this extends (single inheritance) */
    parent: Option[TsTypeRef],
    /** The interfaces this class implements (multiple inheritance of contracts)
      */
    implements: IArray[TsTypeRef],
    /** All members of this class (properties, methods, constructors) */
    members: IArray[TsMember],
    /** Location in the JavaScript output where this class will be placed */
    jsLocation: JsLocation,
    /** Path information for this class */
    codePath: CodePath
) extends TsNamedValueDecl
    with JsLocation.Has
    with HasClassMembers
    with TsNamedDecl {

  override def withCodePath(newCodePath: CodePath): TsDeclClass =
    copy(codePath = newCodePath)

  override def withJsLocation(newLocation: JsLocation): TsDeclClass =
    copy(jsLocation = newLocation)

  override def withName(newName: TsIdentSimple): TsDeclClass =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclClass =
    copy(comments = cs)
}

/** Represents a TypeScript interface declaration. In TypeScript: `interface
  * MyInterface extends BaseInterface { ... }` Interfaces define
  * contracts/shapes for objects and only exist at compile time.
  */
final case class TsDeclInterface(
    /** JSDoc comments for this interface */
    comments: Comments,
    /** Whether this is a declare interface (ambient declaration) */
    declared: Boolean,
    /** The name of the interface */
    name: TsIdentSimple,
    /** Generic type parameters (e.g., <T, U> in interface MyInterface<T, U>) */
    tparams: IArray[TsTypeParam],
    /** The interfaces this interface extends (multiple inheritance) */
    inheritance: IArray[TsTypeRef],
    /** All members of this interface (properties, methods, call signatures) */
    members: IArray[TsMember],
    /** Path information for this interface */
    codePath: CodePath
) extends TsNamedDecl
    with HasClassMembers {

  override def withCodePath(newCodePath: CodePath): TsDeclInterface =
    copy(codePath = newCodePath)

  override def withName(newName: TsIdentSimple): TsDeclInterface =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclInterface =
    copy(comments = cs)
}

/* Other TypeScript declarations */

/** Represents a TypeScript enum declaration. In TypeScript: `enum Color { Red,
  * Green, Blue }` or `const enum Status { Active = 1, Inactive = 0 }` Enums
  * create both a type and a value, allowing reverse lookup by default.
  */
final case class TsDeclEnum(
    /** JSDoc comments for this enum */
    comments: Comments,
    /** Whether this is a declare enum (ambient declaration) */
    declared: Boolean,
    /** Whether this is a const enum (inlined at compile time) */
    isConst: Boolean,
    /** The name of the enum */
    name: TsIdentSimple,
    /** All members/values in this enum */
    members: IArray[TsEnumMember],
    /** Whether this enum creates a runtime value (not just a type) */
    isValue: Boolean,
    /** If this enum is re-exported from another module */
    exportedFrom: Option[TsTypeRef],
    /** Location in the JavaScript output where this enum will be placed */
    jsLocation: JsLocation,
    /** Path information for this enum */
    codePath: CodePath
) extends TsNamedValueDecl
    with JsLocation.Has
    with TsNamedDecl {

  override def withCodePath(newCodePath: CodePath): TsDeclEnum =
    copy(codePath = newCodePath)

  override def withJsLocation(newLocation: JsLocation): TsDeclEnum =
    copy(jsLocation = newLocation)

  override def withName(newName: TsIdentSimple): TsDeclEnum =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclEnum =
    copy(comments = cs)

}

/** Represents a single member/value within a TypeScript enum. In TypeScript:
  * `Red = 1` or `Green` (auto-assigned value) within an enum declaration.
  */
final case class TsEnumMember(
    /** JSDoc comments for this enum member */
    comments: Comments,
    /** The name of this enum member */
    name: TsIdentSimple,
    /** Optional explicit value assignment (e.g., = 1, = "red") */
    expr: Option[TsExpr]
) extends TsTree

/** Represents a TypeScript variable declaration. In TypeScript: `const myVar:
  * string = "hello"` or `let count: number` or `var flag: boolean` Can be
  * const, let, var, or declare var for ambient declarations.
  */
final case class TsDeclVar(
    /** JSDoc comments for this variable */
    comments: Comments,
    /** Whether this is a declare var (ambient declaration) */
    declared: Boolean,
    /** Whether this variable is readonly (const or readonly modifier) */
    readOnly: Boolean,
    /** The name of the variable */
    name: TsIdentSimple,
    /** Optional type annotation */
    tpe: Option[TsType],
    /** Optional initializer expression */
    expr: Option[TsExpr],
    /** Location in the JavaScript output where this variable will be placed */
    jsLocation: JsLocation,
    /** Path information for this variable */
    codePath: CodePath
) extends TsNamedValueDecl
    with JsLocation.Has
    with TsNamedDecl {

  override def withCodePath(newCodePath: CodePath): TsDeclVar =
    copy(codePath = newCodePath)

  override def withJsLocation(newLocation: JsLocation): TsDeclVar =
    copy(jsLocation = newLocation)

  override def withName(newName: TsIdentSimple): TsDeclVar =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclVar =
    copy(comments = cs)
}

/** Represents a TypeScript function declaration. In TypeScript: `function
  * myFunc(x: number): string { ... }` or `declare function myFunc(x: number):
  * string` Top-level function declarations create both a type and a value.
  */
final case class TsDeclFunction(
    /** JSDoc comments for this function */
    comments: Comments,
    /** Whether this is a declare function (ambient declaration) */
    declared: Boolean,
    /** The name of the function */
    name: TsIdentSimple,
    /** The function signature (parameters, return type, type parameters) */
    signature: TsFunSig,
    /** Location in the JavaScript output where this function will be placed */
    jsLocation: JsLocation,
    /** Path information for this function */
    codePath: CodePath
) extends TsNamedValueDecl
    with JsLocation.Has
    with TsNamedDecl {

  override def withCodePath(newCodePath: CodePath): TsDeclFunction =
    copy(codePath = newCodePath)

  override def withJsLocation(newLocation: JsLocation): TsDeclFunction =
    copy(jsLocation = newLocation)

  override def withName(newName: TsIdentSimple): TsDeclFunction =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclFunction =
    copy(comments = cs)
}

/** Represents a TypeScript type alias declaration. In TypeScript: `type MyType
  * \= string | number` or `type GenericType<T> = T[]` Type aliases create new
  * names for existing types and only exist at compile time.
  */
final case class TsDeclTypeAlias(
    /** JSDoc comments for this type alias */
    comments: Comments,
    /** Whether this is a declare type (ambient declaration) */
    declared: Boolean,
    /** The name of the type alias */
    name: TsIdentSimple,
    /** Generic type parameters (e.g., <T, U> in type MyType<T, U>) */
    tparams: IArray[TsTypeParam],
    /** The actual type this alias refers to */
    alias: TsType,
    /** Path information for this type alias */
    codePath: CodePath
) extends TsNamedDecl {
  override def withCodePath(newCodePath: CodePath): TsDeclTypeAlias =
    copy(codePath = newCodePath)

  override def withName(newName: TsIdentSimple): TsDeclTypeAlias =
    copy(name = newName)

  override def withComments(cs: Comments): TsDeclTypeAlias =
    copy(comments = cs)
}

/* Function signatures and parameters */

/** Represents a TypeScript function signature. In TypeScript: `<T>(param1:
  * string, param2: number): boolean` (the signature part of a function) Used in
  * function declarations, method declarations, and function types.
  */
final case class TsFunSig(
    /** JSDoc comments for this function signature */
    comments: Comments,
    /** Generic type parameters (e.g., <T, U>) */
    tparams: IArray[TsTypeParam],
    /** Function parameters with their types */
    params: IArray[TsFunParam],
    /** Return type of the function (None means void or inferred) */
    resultType: Option[TsType]
) extends TsTree

/** Represents a single parameter in a TypeScript function. In TypeScript:
  * `param: string` or `optionalParam?: number` or `...rest: string[]`
  */
final case class TsFunParam(
    /** JSDoc comments for this parameter */
    comments: Comments,
    /** The name of the parameter */
    name: TsIdentSimple,
    /** The type of the parameter (None means inferred or any) */
    tpe: Option[TsType]
) extends TsTree {

  // Parameters are considered equal if they have the same type (name doesn't matter for type checking)
  override def equals(obj: Any): Boolean =
    obj match {
      case that: TsFunParam => tpe === that.tpe
      case _                => false
    }

  override lazy val hashCode: Int = tpe.hashCode
}

/** Represents a TypeScript generic type parameter. In TypeScript: `T`, `T
  * extends string`, `T = string`, or `T extends keyof U = never` Used in
  * generic classes, interfaces, functions, and type aliases.
  */
final case class TsTypeParam(
    /** JSDoc comments for this type parameter */
    comments: Comments,
    /** The name of the type parameter (e.g., T, U, K) */
    name: TsIdentSimple,
    /** Optional constraint (e.g., T extends string) */
    upperBound: Option[TsType],
    /** Optional default type (e.g., T = string) */
    default: Option[TsType]
) extends TsTree

object TsTypeParam {

  /** Converts type parameters to type arguments for instantiation. Transforms
    * `<T, U>` into `T, U` for use in type references.
    */
  def asTypeArgs(tps: IArray[TsTypeParam]): IArray[TsTypeRef] =
    tps.map(tp => TsTypeRef(tp.name))
}

/** Base class for TypeScript literal values. In TypeScript: `42`, `"hello"`,
  * `true`, `false` Represents compile-time constant values.
  */
sealed abstract class TsLiteral(repr: String) extends TsTree {
  val literal = repr
}

object TsLiteral {

  /** Numeric literal: `42`, `3.14`, `0xFF` */
  final case class Num(value: String) extends TsLiteral(value)

  /** String literal: `"hello"`, `'world'`, `` `template` `` */
  final case class Str(value: String) extends TsLiteral(value)

  /** Boolean literal: `true`, `false` */
  final case class Bool(value: Boolean) extends TsLiteral(value.toString)
}

/** Base trait for TypeScript identifiers (names). Represents any kind of name
  * used in TypeScript code.
  */
sealed trait TsIdent extends TsTree {
  val value: String
}

/** Represents a simple TypeScript identifier. In TypeScript: `myVariable`,
  * `MyClass`, `functionName` The most common type of identifier for variables,
  * functions, classes, etc.
  */
final case class TsIdentSimple(value: String) extends TsIdent

/** Represents an identifier that comes from an import. In TypeScript: when you
  * `import { something } from "module"`, `something` becomes a TsIdentImport
  * Links the identifier back to its source module.
  */
final case class TsIdentImport(from: TsIdentModule) extends TsIdent {
  override val value: String = from.value
}

/** Represents a TypeScript module identifier. In TypeScript: `"lodash"`,
  * `"@types/node"`, `"./relative-module"` Used in import/export statements and
  * module declarations.
  */
final case class TsIdentModule(
    scopeOpt: Option[String],
    fragments: List[String]
) extends TsIdent {
  @deprecated("this doesnt really work for node", "")
  def inLibrary: TsIdentLibrary =
    scopeOpt match {
      case None        => TsIdentLibrarySimple(fragments.head)
      case Some(scope) => TsIdentLibraryScoped(scope, fragments.head)
    }

  /** Constructs the full module name. Examples: "lodash", "@types/node",
    * "@scope/package/submodule"
    */
  val value: String =
    scopeOpt match {
      case None        => fragments.mkString("/")
      case Some(scope) => "@" + scope + "/" + fragments.mkString("/")
    }

  override lazy val hashCode: Int = value.hashCode

  override def equals(obj: Any): Boolean =
    obj match {
      case other: TsIdentModule if other.hashCode == hashCode =>
        other.value == value
      case _ => false
    }
}

object TsIdentModule {

  /** Creates a module identifier from a library identifier */
  def fromLibrary(lib: TsIdentLibrary): TsIdentModule =
    lib match {
      case TsIdentLibrarySimple(name) =>
        TsIdentModule(None, name.split("\\.").toList)
      case TsIdentLibraryScoped(scope, name) =>
        TsIdentModule(Some(scope), name.split("\\.").toList)
    }

  /** Creates a simple module identifier with a single fragment */
  def simple(s: String): TsIdentModule =
    TsIdentModule(None, s :: Nil)

  implicit val encodes: Encoder[TsIdentModule] =
    io.circe.generic.semiauto.deriveEncoder
  implicit val decodes: Decoder[TsIdentModule] =
    io.circe.generic.semiauto.deriveDecoder
}

/** Represents a TypeScript library/package identifier. In TypeScript:
  * `"lodash"`, `"@types/node"`, `"react"` Used to identify npm packages and
  * their type definitions.
  */
sealed trait TsIdentLibrary extends TsIdent {

  /** Internal representation used for file naming and disambiguation. Converts
    * scoped packages like "@scope/name" to "scope__name"
    */
  def `__value`: String = this match {
    case TsIdentLibraryScoped(scope, name) => s"${scope}__$name"
    case TsIdentLibrarySimple(value)       => value
  }
}

object TsIdentLibrary {
  implicit val ordering: Ordering[TsIdentLibrary] =
    Ordering[String].on[TsIdentLibrary](_.value)
  implicit val TsIdentLibraryDecoder: Decoder[TsIdentLibrary] =
    Decoder[String].map(TsIdentLibrary.apply)
  implicit val TsIdentLibraryEncoder: Encoder[TsIdentLibrary] =
    Encoder[String].contramap[TsIdentLibrary](_.value)
  implicit val TsIdentLibraryKeyDec: KeyDecoder[TsIdentLibrary] =
    KeyDecoder[String].map(TsIdentLibrary.apply)
  implicit val TsIdentLibraryKeyEnc: KeyEncoder[TsIdentLibrary] =
    KeyEncoder[String].contramap[TsIdentLibrary](_.value)

  /** Regex for scoped packages: @scope/name */
  val Scoped = "@([^/]+)/(.+)".r

  /** Regex for internal scoped representation: scope__name */
  val Scoped__ = "(.+)__(.+)".r

  /** Parses a string into a library identifier. Handles both simple names and
    * scoped packages.
    */
  def apply(str: String): TsIdentLibrary =
    str match {
      case Scoped("types", name)   => apply(name) // @types/babel__core exists
      case Scoped(scope, name)     => TsIdentLibraryScoped(scope, name)
      case Scoped__("types", name) => apply(name)
      case Scoped__(scope, name)   => TsIdentLibraryScoped(scope, name)
      case other                   => TsIdentLibrarySimple(other)
    }
}

/** Represents a simple (non-scoped) library identifier. In TypeScript:
  * `"lodash"`, `"react"`, `"express"`
  */
final case class TsIdentLibrarySimple(value: String) extends TsIdentLibrary

/** Represents a scoped library identifier. In TypeScript: `"@types/node"`,
  * `"@angular/core"`, `"@babel/parser"`
  */
final case class TsIdentLibraryScoped(scope: String, name: String)
    extends TsIdentLibrary {
  val value: String = s"@$scope/$name"
}

object TsIdent {
  implicit val encodes: Encoder[TsIdent] =
    io.circe.generic.semiauto.deriveEncoder
  implicit val decodes: Decoder[TsIdent] =
    io.circe.generic.semiauto.deriveDecoder
  implicit val ordering: Ordering[TsIdent] =
    Ordering[String].on[TsIdent](_.value)

  /** Creates a simple identifier from a string */
  def apply(str: String): TsIdentSimple =
    TsIdentSimple(str)

  /** Extracts the string value from any identifier */
  def unapply(ident: TsIdent): Some[String] =
    Some(ident.value)

  // Special identifiers used in TypeScript
  /** The `this` keyword identifier */
  val `this`: TsIdentSimple = TsIdent("this")

  /** Special identifier for function application/call syntax */
  val Apply: TsIdentSimple = TsIdent(
    "<apply>"
  ) // keep in sync with Name.necessaryRewrite
  /** Special identifier for global scope declarations */
  val Global: TsIdentSimple = TsIdent(
    "<global>"
  ) // keep in sync with Name.necessaryRewrite
  /** Special identifier for destructured parameters without explicit names */
  val Destructured: TsIdentSimple = TsIdent(
    "<destructured>"
  ) // for parameters with no name.

  // Common TypeScript/JavaScript identifiers
  /** The `update` method identifier (common in immutable libraries) */
  val update: TsIdentSimple = TsIdent("update")

  /** The `prototype` property identifier */
  val prototype: TsIdentSimple = TsIdent("prototype")

  /** The `constructor` property identifier */
  val constructor: TsIdentSimple = TsIdent("constructor")

  /** The `default` export identifier */
  val default: TsIdentSimple = TsIdent("default")

  /** Special identifier for namespaced declarations */
  val namespaced: TsIdentSimple = TsIdent(
    "^"
  ) // keep in sync with Name.necessaryRewrite
  /** Special identifier for namespaced class declarations */
  val namespacedCls: TsIdentSimple = TsIdent("Class")

  /** The `Symbol` global identifier */
  val Symbol: TsIdentSimple = TsIdent("Symbol")

  /** Dummy identifier for placeholder purposes */
  val dummy: TsIdentSimple = TsIdent("dummy")

  // Special library identifiers
  /** Placeholder library identifier for testing/dummy purposes */
  val dummyLibrary: TsIdentLibrary = TsIdentLibrarySimple("dummyLibrary")

  /** TypeScript standard library identifier */
  val std: TsIdentLibrary = TsIdentLibrarySimple("std")

  /** Node.js type definitions library identifier */
  val node: TsIdentLibrary = TsIdentLibrarySimple("node")
}

/** Represents a qualified TypeScript identifier (dotted name). In TypeScript:
  * `MyNamespace.MyClass`, `React.Component`, `std.Array` Used for accessing
  * nested declarations and namespaced types.
  */
final case class TsQIdent(parts: IArray[TsIdent]) extends TsTree {

  /** Appends a single identifier to create a longer qualified name */
  def +(tsIdent: TsIdent): TsQIdent =
    TsQIdent(parts :+ tsIdent)

  /** Appends multiple identifiers to create a longer qualified name */
  def ++(tsIdents: IArray[TsIdent]): TsQIdent =
    TsQIdent(parts ++ tsIdents)
}

object TsQIdent {
  implicit val encodes: Encoder[TsQIdent] =
    io.circe.generic.semiauto.deriveEncoder
  implicit val decodes: Decoder[TsQIdent] =
    io.circe.generic.semiauto.deriveDecoder
  implicit val ordering: Ordering[TsQIdent] = Ordering.by(_.parts)

  /** Creates a qualified identifier from string parts */
  def of(ss: String*) = TsQIdent(IArray.fromTraversable(ss.map(TsIdent.apply)))

  /** Creates a qualified identifier from a single identifier */
  def of(tsIdent: TsIdent) = TsQIdent(IArray(tsIdent))

  /** Empty qualified identifier */
  val empty: TsQIdent = TsQIdent(IArray.Empty)

  // TypeScript primitive types (lowercase)
  /** The `any` type - accepts any value */
  val any: TsQIdent = TsQIdent.of("any")

  /** The `bigint` type - for large integers */
  val bigint: TsQIdent = TsQIdent.of("bigint")

  /** The `number` type - for numeric values */
  val number: TsQIdent = TsQIdent.of("number")

  /** The `boolean` type - for true/false values */
  val boolean: TsQIdent = TsQIdent.of("boolean")

  /** The `never` type - represents values that never occur */
  val never: TsQIdent = TsQIdent.of("never")

  /** The `null` type - represents null values */
  val `null`: TsQIdent = TsQIdent.of("null")

  /** The `object` type - for non-primitive values */
  val `object`: TsQIdent = TsQIdent.of("object")

  /** The `string` type - for text values */
  val string: TsQIdent = TsQIdent.of("string")

  /** The `symbol` type - for unique identifiers */
  val symbol: TsQIdent = TsQIdent.of("symbol")

  /** The `undefined` type - represents undefined values */
  val undefined: TsQIdent = TsQIdent.of("undefined")

  /** The `unknown` type - type-safe alternative to any */
  val unknown: TsQIdent = TsQIdent.of("unknown")

  /** The `void` type - represents absence of value */
  val void: TsQIdent = TsQIdent.of("void")

  /** Set of all primitive type identifiers */
  val Primitive =
    Set(
      any,
      bigint,
      number,
      boolean,
      never,
      `null`,
      `object`,
      string,
      symbol,
      undefined,
      unknown,
      void
    )

  // TypeScript built-in object types (capitalized)
  /** The `Array` constructor type */
  val Array: TsQIdent = TsQIdent.of("Array")

  /** The `BigInt` constructor type */
  val BigInt: TsQIdent = TsQIdent.of("BigInt")

  /** The `ReadonlyArray` utility type */
  val ReadonlyArray: TsQIdent = TsQIdent.of("ReadonlyArray")

  /** The `Boolean` constructor type */
  val Boolean: TsQIdent = TsQIdent.of("Boolean")

  /** The `Function` constructor type */
  val Function: TsQIdent = TsQIdent.of("Function")

  /** The `Object` constructor type */
  val Object: TsQIdent = TsQIdent.of("Object")

  /** The `String` constructor type */
  val String: TsQIdent = TsQIdent.of("String")

  /** Standard library types (prefixed with std namespace) */
  object Std {
    val Array = TsQIdent(IArray(TsIdent.std, TsIdent("Array")))
    val BigInt = TsQIdent(IArray(TsIdent.std, TsIdent("BigInt")))
    val Boolean = TsQIdent(IArray(TsIdent.std, TsIdent("Boolean")))
    val ConcatArray = TsQIdent(IArray(TsIdent.std, TsIdent("ConcatArray")))
    val Function = TsQIdent(IArray(TsIdent.std, TsIdent("Function")))
    val Object = TsQIdent(IArray(TsIdent.std, TsIdent("Object")))
    val Promise = TsQIdent(IArray(TsIdent.std, TsIdent("Promise")))
    val PromiseLike = TsQIdent(IArray(TsIdent.std, TsIdent("PromiseLike")))
    val Readonly = TsQIdent(IArray(TsIdent.std, TsIdent("Readonly")))
    val ReadonlyArray = TsQIdent(IArray(TsIdent.std, TsIdent("ReadonlyArray")))
    val Record = TsQIdent(IArray(TsIdent.std, TsIdent("Record")))
    val String = TsQIdent(IArray(TsIdent.std, TsIdent("String")))
  }
}

/* TypeScript type system representations */

/** Base trait for all TypeScript types. In TypeScript: `string`, `number`,
  * `MyInterface`, `string | number`, `{ prop: string }`, etc. Represents any
  * type expression that can appear in type annotations.
  */
sealed abstract class TsType extends TsTree

object TsType {

  /** Checks if the given members represent a mapped type. Mapped types have
    * exactly one TsMemberTypeMapped member.
    */
  def isTypeMapping(members: IArray[TsMember]): Boolean =
    members match {
      case IArray.exactlyOne(_: TsMemberTypeMapped) => true
      case _                                        => false
    }
}

/** Represents a TypeScript type reference. In TypeScript: `MyClass`,
  * `Array<string>`, `Promise<number>`, `React.Component<Props>` References to
  * named types, possibly with generic type arguments.
  */
final case class TsTypeRef(
    /** JSDoc comments for this type reference */
    comments: Comments,
    /** The qualified name being referenced */
    name: TsQIdent,
    /** Generic type arguments (e.g., <string, number>) */
    tparams: IArray[TsType]
) extends TsType

object TsTypeRef {

  /** Creates a type reference from a simple identifier */
  def apply(tsIdent: TsIdent): TsTypeRef =
    apply(TsQIdent.of(tsIdent))

  /** Creates a type reference from a qualified identifier */
  def apply(tsQIdent: TsQIdent): TsTypeRef =
    TsTypeRef(NoComments, tsQIdent, Empty)

  // Common TypeScript type references
  val any = TsTypeRef(NoComments, TsQIdent.any, Empty)
  val boolean = TsTypeRef(NoComments, TsQIdent.boolean, Empty)
  val Boolean = TsTypeRef(NoComments, TsQIdent.Boolean, Empty)
  val Symbol = TsTypeRef(NoComments, TsQIdent.symbol, Empty)
  val `object` = TsTypeRef(NoComments, TsQIdent.`object`, Empty)
  val Object = TsTypeRef(NoComments, TsQIdent.Object, Empty)
  val string = TsTypeRef(NoComments, TsQIdent.string, Empty)
  val String = TsTypeRef(NoComments, TsQIdent.String, Empty)
  val never = TsTypeRef(NoComments, TsQIdent.never, Empty)
  val number = TsTypeRef(NoComments, TsQIdent.number, Empty)
  val `null` = TsTypeRef(NoComments, TsQIdent.`null`, Empty)
  val void = TsTypeRef(NoComments, TsQIdent.void, Empty)
  val undefined = TsTypeRef(NoComments, TsQIdent.undefined, Empty)
}

/** Represents a TypeScript literal type. In TypeScript: `42`, `"hello"`,
  * `true`, `false` Types that represent specific literal values.
  */
final case class TsTypeLiteral(literal: TsLiteral) extends TsType

/** Represents a TypeScript object type. In TypeScript: `{ prop: string;
  * method(): void }`, `{ [key: string]: any }` Anonymous object types with
  * properties, methods, and index signatures.
  */
final case class TsTypeObject(
    /** JSDoc comments for this object type */
    comments: Comments,
    /** Properties, methods, and signatures of this object type */
    members: IArray[TsMember]
) extends TsType
    with HasClassMembers

/** Represents a TypeScript function type. In TypeScript: `(x: number, y:
  * string) => boolean`, `<T>(arg: T) => T` Function types with parameters and
  * return types.
  */
final case class TsTypeFunction(signature: TsFunSig) extends TsType

/** Represents a TypeScript constructor type. In TypeScript: `new (x: number) =>
  * MyClass`, `abstract new () => AbstractClass` Types for constructor
  * functions.
  */
final case class TsTypeConstructor(
    /** Whether this is an abstract constructor */
    isAbstract: Boolean,
    /** The constructor function signature */
    signature: TsTypeFunction
) extends TsType

/** Represents a TypeScript type predicate with 'is'. In TypeScript: `x is
  * string`, `value is MyType` Used in type guard functions to narrow types.
  */
final case class TsTypeIs(
    /** The parameter being checked */
    ident: TsIdent,
    /** The type being asserted */
    tpe: TsType
) extends TsType

/** Represents a TypeScript assertion signature. In TypeScript: `asserts x`,
  * `asserts x is string` Used in assertion functions that throw if condition is
  * false.
  */
final case class TsTypeAsserts(
    /** The parameter being asserted */
    ident: TsIdentSimple,
    /** Optional type being asserted */
    isOpt: Option[TsType]
) extends TsType

/** Represents a single element in a TypeScript tuple type. In TypeScript:
  * `string` in `[string, number]` or `name: string` in `[name: string, age:
  * number]` Tuple elements can optionally have labels for better documentation.
  */
final case class TsTupleElement(
    /** Optional label for this tuple element */
    label: Option[TsIdent],
    /** The type of this tuple element */
    tpe: TsType
)

object TsTupleElement {

  /** Creates an unlabeled tuple element */
  def unlabeled(tpe: TsType): TsTupleElement = TsTupleElement(label = None, tpe)
}

/** Represents a TypeScript tuple type. In TypeScript: `[string, number]`,
  * `[name: string, age: number]`, `[string, ...number[]]` Fixed-length arrays
  * with specific types for each position.
  */
final case class TsTypeTuple(elems: IArray[TsTupleElement]) extends TsType

/** Represents a TypeScript typeof query. In TypeScript: `typeof myVariable`,
  * `typeof MyClass.prototype` Gets the type of a value expression.
  */
final case class TsTypeQuery(expr: TsQIdent) extends TsType

/** Represents a TypeScript rest/spread type. In TypeScript: `...string[]` in
  * tuple types or function parameters Represents variable-length sequences of a
  * type.
  */
final case class TsTypeRepeated(underlying: TsType) extends TsType

/** Represents a TypeScript keyof operator. In TypeScript: `keyof MyInterface`,
  * `keyof typeof myObject` Gets the union of all property names of a type.
  */
final case class TsTypeKeyOf(key: TsType) extends TsType

/** Represents a TypeScript indexed access type. In TypeScript: `MyType[K]`,
  * `MyArray[number]`, `MyObject["property"]` Accesses the type of a property by
  * key.
  */
final case class TsTypeLookup(
    /** The type being indexed */
    from: TsType,
    /** The key/index type */
    key: TsType
) extends TsType

/** Represents the TypeScript 'this' type. In TypeScript: `this` in method
  * return types or parameter types Refers to the type of the current instance.
  */
final case class TsTypeThis() extends TsType

/** Represents a TypeScript intersection type. In TypeScript: `A & B & C`
  * Creates a type that has all properties of all intersected types.
  */
final case class TsTypeIntersect(types: IArray[TsType]) extends TsType

object TsTypeIntersect {

  /** Flattens nested intersection types into a single level */
  private def flatten(types: IArray[TsType]): IArray[TsType] =
    types.flatMap {
      case TsTypeIntersect(nested) => flatten(nested)
      case other                   => IArray(other)
    }

  /** Creates a simplified intersection type, combining object types where
    * possible. Merges multiple object types into a single object type for
    * better readability.
    */
  def simplified(types: IArray[TsType]): TsType = {
    val withCombinedObjects = types.partitionCollect {
      case x: TsTypeObject if !TsType.isTypeMapping(x.members) => x
    } match {
      case (Empty, all)              => all
      case (IArray.exactlyOne(_), _) => types // just keep order
      case (objects, rest) =>
        TsTypeObject(NoComments, objects.flatMap(_.members).distinct) +: rest
    }
    flatten(withCombinedObjects).distinct match {
      case IArray.Empty           => TsTypeRef.never
      case IArray.exactlyOne(one) => one
      case more                   => new TsTypeIntersect(more)
    }
  }
}

/** Represents a TypeScript union type. In TypeScript: `string | number |
  * boolean` Creates a type that can be any one of the specified types.
  */
final case class TsTypeUnion(types: IArray[TsType]) extends TsType

object TsTypeUnion {

  /** Flattens nested union types into a single level */
  private def flatten(types: IArray[TsType]): IArray[TsType] =
    types.flatMap {
      case TsTypeUnion(nested) => flatten(nested)
      case other               => IArray(other)
    }

  /** Creates a simplified union type, removing duplicates and flattening nested
    * unions. Returns never for empty unions, single type for one-element
    * unions.
    */
  def simplified(types: IArray[TsType]): TsType =
    flatten(types).distinct match {
      case IArray.Empty           => TsTypeRef.never
      case IArray.exactlyOne(one) => one
      case more                   => new TsTypeUnion(more)
    }
}

/** Base trait for TypeScript type predicates and conditional logic. Used in
  * advanced type-level programming with conditional types.
  */
sealed trait TsTypePredicate extends TsType

/** Represents a TypeScript conditional type. In TypeScript: `T extends string ?
  * string[] : never` Type-level if-then-else logic based on type relationships.
  */
final case class TsTypeConditional(
    /** The condition to test */
    pred: TsType,
    /** Type to use if condition is true */
    ifTrue: TsType,
    /** Type to use if condition is false */
    ifFalse: TsType
) extends TsTypePredicate

/** Represents a TypeScript extends clause in conditional types. In TypeScript:
  * `T extends (...args: any[]) => infer R ? R : any` Tests if one type
  * extends/is assignable to another.
  */
final case class TsTypeExtends(
    /** The type being tested */
    tpe: TsType,
    /** The type it should extend */
    `extends`: TsType
) extends TsTypePredicate

/** Represents a TypeScript infer keyword in conditional types. In TypeScript:
  * `infer R` in `T extends (...args: any[]) => infer R ? R : any` Captures and
  * names a type for use in the conditional type's branches.
  */
final case class TsTypeInfer(tparam: TsTypeParam) extends TsTypePredicate

/* TypeScript class and interface members */

/** Base trait for all TypeScript class and interface members. In TypeScript:
  * properties, methods, constructors, call signatures, index signatures, etc.
  * Represents anything that can appear inside a class or interface body.
  */
sealed abstract class TsMember extends TsTree {

  /** The visibility level (public, private, protected) of this member */
  def level: TsProtectionLevel
}

/** Represents a TypeScript call signature. In TypeScript: `(x: number): string`
  * inside an interface or object type Allows objects to be called like
  * functions.
  */
final case class TsMemberCall(
    /** JSDoc comments for this call signature */
    comments: Comments,
    /** Visibility level of this call signature */
    level: TsProtectionLevel,
    /** The function signature for the call */
    signature: TsFunSig
) extends TsMember

/** Represents a TypeScript constructor signature. In TypeScript: `new (x:
  * number): MyClass` inside an interface Allows objects to be used as
  * constructors.
  */
final case class TsMemberCtor(
    /** JSDoc comments for this constructor signature */
    comments: Comments,
    /** Visibility level of this constructor signature */
    level: TsProtectionLevel,
    /** The function signature for the constructor */
    signature: TsFunSig
) extends TsMember

/** Represents a TypeScript method declaration. In TypeScript: `myMethod(x:
  * number): string` in a class or interface Methods are functions that belong
  * to classes or interfaces.
  */
final case class TsMemberFunction(
    /** JSDoc comments for this method */
    comments: Comments,
    /** Visibility level (public, private, protected) */
    level: TsProtectionLevel,
    /** The name of the method */
    name: TsIdentSimple,
    /** The type of method (normal, getter, setter) */
    methodType: MethodType,
    /** The method's signature (parameters, return type, generics) */
    signature: TsFunSig,
    /** Whether this is a static method */
    isStatic: Boolean,
    /** Whether this method is readonly */
    isReadOnly: Boolean
) extends TsMember

/** Represents different types of indexing in TypeScript. Used for index
  * signatures and computed property access.
  */
sealed trait Indexing extends TsTree
object Indexing {

  /** Dictionary-style indexing: `[key: string]: ValueType` */
  case class Dict(name: TsIdent, tpe: TsType) extends Indexing

  /** Single property indexing: `[K in keyof T]` */
  case class Single(name: TsQIdent) extends Indexing
}

/** Represents a TypeScript index signature. In TypeScript: `[key: string]: any`
  * or `[index: number]: string` Allows objects to have properties with computed
  * names.
  */
final case class TsMemberIndex(
    /** JSDoc comments for this index signature */
    comments: Comments,
    /** Whether the indexed values are readonly */
    isReadOnly: Boolean,
    /** Visibility level of this index signature */
    level: TsProtectionLevel,
    /** The indexing pattern (key type and name) */
    indexing: Indexing,
    /** The type of values stored at indexed locations */
    valueType: Option[TsType]
) extends TsMember

/** Represents a TypeScript mapped type member. In TypeScript: `{ [K in keyof
  * T]: T[K] }` or `{ readonly [K in keyof T]?: T[K] }` Used for transforming
  * types by mapping over their properties.
  */
final case class TsMemberTypeMapped(
    /** JSDoc comments for this mapped type */
    comments: Comments,
    /** Visibility level of this mapped type */
    level: TsProtectionLevel,
    /** Readonly modifier (readonly, +readonly, -readonly, or none) */
    readonly: ReadonlyModifier,
    /** The key variable name (e.g., K in `[K in keyof T]`) */
    key: TsIdent,
    /** The type being mapped over (e.g., `keyof T`) */
    from: TsType,
    /** Optional key remapping (e.g., `as \`prefix_\${K}\``) */
    as: Option[TsType],
    /** Optional modifier (+?, -?, ?, or none) */
    optionalize: OptionalModifier,
    /** The resulting type for each mapped property */
    to: TsType
) extends TsMember

/** Represents a TypeScript property declaration. In TypeScript: `myProp:
  * string` or `static readonly count: number = 0` Properties store data in
  * classes and interfaces.
  */
final case class TsMemberProperty(
    /** JSDoc comments for this property */
    comments: Comments,
    /** Visibility level (public, private, protected) */
    level: TsProtectionLevel,
    /** The name of the property */
    name: TsIdentSimple,
    /** Optional type annotation */
    tpe: Option[TsType],
    /** Optional initializer expression */
    expr: Option[TsExpr],
    /** Whether this is a static property */
    isStatic: Boolean,
    /** Whether this property is readonly */
    isReadOnly: Boolean
) extends TsMember

/* TypeScript import and export declarations */

/** Represents what is being imported in a TypeScript import statement. Covers
  * the different syntaxes for importing from modules.
  */
sealed trait TsImported extends TsTree
object TsImported {

  /** Default import or single named import. In TypeScript: `import React from
    * "react"` or `import { useState } from "react"`
    */
  final case class Ident(ident: TsIdentSimple) extends TsImported

  /** Destructured/named imports with optional aliasing. In TypeScript: `import
    * { useState as state, useEffect } from "react"` The tuple represents
    * (originalName, optionalAlias)
    */
  final case class Destructured(
      idents: IArray[(TsIdent, Option[TsIdentSimple])]
  ) extends TsImported

  /** Star/namespace import. In TypeScript: `import * as React from "react"` or
    * `import * from "module"`
    */
  final case class Star(asOpt: Option[TsIdentSimple]) extends TsImported
}

/** Represents the source of a TypeScript import. Specifies where the import is
  * coming from.
  */
sealed trait TsImportee extends TsTree
object TsImportee {

  /** CommonJS-style require import. In TypeScript: `import foo =
    * require("module")`
    */
  final case class Required(from: TsIdentModule) extends TsImportee

  /** ES6-style module import. In TypeScript: `import { foo } from "module"`
    */
  final case class From(from: TsIdentModule) extends TsImportee

  /** Local/relative import. In TypeScript: `import { foo } from
    * "./local-module"`
    */
  final case class Local(qident: TsQIdent) extends TsImportee
}

/** Represents a complete TypeScript import declaration. In TypeScript: `import
  * { useState } from "react"` or `import type { Props } from "./types"`
  */
final case class TsImport(
    /** Whether this is a type-only import */
    typeOnly: Boolean,
    /** What is being imported */
    imported: IArray[TsImported],
    /** Where it's being imported from */
    from: TsImportee
) extends TsDecl
    with TsTree

/* TypeScript export declarations */

/** Represents what is being exported in a TypeScript export statement. Covers
  * the different syntaxes for exporting from modules.
  */
sealed trait TsExportee extends TsTree
object TsExportee {

  /** Named exports with optional aliasing. In TypeScript: `export { foo, bar as
    * baz }` or `export { foo } from "module"` The tuple represents
    * (originalName, optionalAlias)
    */
  case class Names(
      idents: IArray[(TsQIdent, Option[TsIdentSimple])],
      fromOpt: Option[TsIdentModule]
  ) extends TsExportee

  /** Direct export of a declaration. In TypeScript: `export class MyClass {}`
    * or `export function myFunc() {}`
    */
  case class Tree(decl: TsDecl) extends TsExportee

  /** Star/namespace export. In TypeScript: `export * from "module"` or `export
    * * as namespace from "module"`
    */
  case class Star(as: Option[TsIdentSimple], from: TsIdentModule)
      extends TsExportee
}

/** Represents a complete TypeScript export declaration. In TypeScript: `export
  * { foo }` or `export type { Props }` or `export default MyClass`
  */
final case class TsExport(
    /** JSDoc comments for this export */
    comments: Comments,
    /** Whether this is a type-only export */
    typeOnly: Boolean,
    /** The type of export (default, named, etc.) */
    tpe: ExportType,
    /** What is being exported */
    exported: TsExportee
) extends TsDecl

/** Represents a TypeScript export-as-namespace declaration. In TypeScript:
  * `export as namespace MyLibrary` Used in UMD modules to specify the global
  * variable name.
  */
final case class TsExportAsNamespace(ident: TsIdentSimple) extends TsDecl