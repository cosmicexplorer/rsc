// Copyright (c) 2018 Twitter, Inc.
// Licensed under the Apache License, Version 2.0 (see LICENSE.md).
package rsc.syntax

import spray.json._
import scala.{Symbol => StdlibSymbol}

// implicit object TermJ extends JsonFormat[Term] {
//     def write(v: Term) = {
//       val (classKey: String, convertedJson: JsValue) = v match {
//       }
//       JsArray(JsString(classKey), convertedJson)
//     }
//     def read(o: JsValue) = o match {
//       case JsArray(Vector(JsString(classKey), obj)) => classKey match {
//         case _ => deserializationError(s"unrecognized classKey $classKey for Term")
//       }
//       case _ => deserializationError(s"unrecognized Term")
//   }
// }

final object TreesProtocol extends DefaultJsonProtocol {

  implicit val ctorId = jsonFormat0(CtorId.apply)
  object NamedIdJ extends JsonFormat[NamedId] {
    def write(v: NamedId) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: CtorId => ("CtorId", v.asInstanceOf[CtorId].toJson)
        case _: PatId => ("PatId", v.asInstanceOf[PatId].toJson)
        case _: TermId => ("TermId", v.asInstanceOf[TermId].toJson)
        case _: TptId => ("TptId", v.asInstanceOf[TptId].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "CtorId" => obj.convertTo[CtorId]
        case "PatId" => obj.convertTo[PatId]
        case "TermId" => obj.convertTo[TermId]
        case "TptId" => obj.convertTo[TptId]
        case _ => deserializationError(s"unrecognized classKey $classKey for NamedId")
      }
      case _ => deserializationError(s"unrecognized NamedId")
    }
  }
  implicit val namedId: JsonFormat[NamedId] = lazyFormat(NamedIdJ)

  object ThisIdJ extends JsonFormat[ThisId] {
    def write(v: ThisId) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: AmbigId => ("AmbigId", v.asInstanceOf[AmbigId].toJson)
        case _: AnonId => ("AnonId", v.asInstanceOf[AnonId].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "AmbigId" => obj.convertTo[AmbigId]
        case "AnonId" => obj.convertTo[AnonId]
        case _ => deserializationError(s"unrecognized classKey $classKey for ThisId")
      }
      case _ => deserializationError(s"unrecognized ThisId")
    }
  }
  implicit val thisId: JsonFormat[ThisId] = lazyFormat(ThisIdJ)

  object SuperIdJ extends JsonFormat[SuperId] {
    def write(v: SuperId) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: AmbigId => ("AmbigId", v.asInstanceOf[AmbigId].toJson)
        case _: AnonId => ("AnonId", v.asInstanceOf[AnonId].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "AmbigId" => obj.convertTo[AmbigId]
        case "AnonId" => obj.convertTo[AnonId]
        case _ => deserializationError(s"unrecognized classKey $classKey for SuperId")
      }
      case _ => deserializationError(s"unrecognized SuperId")
    }
  }
  implicit val superId: JsonFormat[SuperId] = lazyFormat(SuperIdJ)

  implicit object TermPathJ extends JsonFormat[TermPath] {
    def write(v: TermPath) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: TermId => ("TermId", v.asInstanceOf[TermId].toJson)
        case _: TermSelect => ("TermSelect", v.asInstanceOf[TermSelect].toJson)
        case _: TermSuper => ("TermSuper", v.asInstanceOf[TermSuper].toJson)
        case _: TermThis => ("TermThis", v.asInstanceOf[TermThis].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "TermId" => obj.convertTo[TermId]
        case "TermSelect" => obj.convertTo[TermSelect]
        case "TermSuper" => obj.convertTo[TermSuper]
        case "TermThis" => obj.convertTo[TermThis]
        case _ => deserializationError(s"unrecognized classKey $classKey for TermPath")
      }
      case _ => deserializationError(s"unrecognized TermPath")
    }
  }

  implicit object TptPathJ extends JsonFormat[TptPath] {
    def write(v: TptPath) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: TptId => ("TptId", v.asInstanceOf[TptId].toJson)
        case _: TptProject => ("TptProject", v.asInstanceOf[TptProject].toJson)
        case _: TptSelect => ("TptSelect", v.asInstanceOf[TptSelect].toJson)
        case _: TptSingleton => ("TptSingleton", v.asInstanceOf[TptSingleton].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "TptId" => obj.convertTo[TptId]
        case "TptProject" => obj.convertTo[TptProject]
        case "TptSelect" => obj.convertTo[TptSelect]
        case "TptSingleton" => obj.convertTo[TptSingleton]
        case _ => deserializationError(s"unrecognized classKey $classKey for TptPath")
      }
      case _ => deserializationError(s"unrecognized TptPath")
    }
  }

  implicit object UnambigPathJ extends JsonFormat[UnambigPath] {
    def write(v: UnambigPath) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: TermPath => ("TermPath", v.asInstanceOf[TermPath].toJson)
        case _: TptPath => ("TptPath", v.asInstanceOf[TptPath].toJson)
        case _: NamedId => ("NamedId", v.asInstanceOf[NamedId].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "TermPath" => obj.convertTo[TermPath]
        case "TptPath" => obj.convertTo[TptPath]
        case "NamedId" => obj.convertTo[NamedId]
        case _ => deserializationError(s"unrecognized classKey $classKey for UnambigPath")
      }
      case _ => deserializationError("unrecognized UnambigPath")
    }
  }

  object PathJ extends JsonFormat[Path] {
    def write(v: Path) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: AmbigPath => ("AmbigPath", v.asInstanceOf[AmbigPath].toJson)
        case _: UnambigPath => ("UnambigPath", v.asInstanceOf[UnambigPath].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "AmbigPath" => obj.convertTo[AmbigPath]
        case "UnambigPath" => obj.convertTo[UnambigPath]
        case _ => deserializationError(s"unrecognized classKey $classKey for Path")
      }
      case _ => deserializationError("unrecognized Path")
    }
  }
  implicit val path: JsonFormat[Path] = lazyFormat(PathJ)

  implicit val ambigId: JsonFormat[AmbigId] = jsonFormat1(AmbigId)
  implicit val ambigSelect: JsonFormat[AmbigSelect] = lazyFormat(jsonFormat(
    AmbigSelect, "qual", "id"))
  implicit object AmbigPathJ extends JsonFormat[AmbigPath] {
    def write(v: AmbigPath) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: AmbigId => ("AmbigId", v.asInstanceOf[AmbigId].toJson)
        case _: AmbigSelect => ("AmbigSelect", v.asInstanceOf[AmbigSelect].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "AmbigId" => obj.convertTo[AmbigId]
        case "AmbigSelect" => obj.convertTo[AmbigSelect]
        case _ => deserializationError(s"unrecognized classKey $classKey for AmbigPath")
      }
      case _ => deserializationError("unrecognized AmbigPath")
    }
  }
  implicit val ambigPath: JsonFormat[AmbigPath] = lazyFormat(ambigPath)

  implicit val anonId: JsonFormat[AnonId] = jsonFormat0(AnonId)
  implicit object UnambigIdJ extends JsonFormat[UnambigId] {
    def write(v: UnambigId) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: AnonId => ("AnonId", v.asInstanceOf[AnonId].toJson)
        case _: NamedId => ("NamedId", v.asInstanceOf[NamedId].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "AnonId" => obj.convertTo[AnonId]
        case "NamedId" => obj.convertTo[NamedId]
        case _ => deserializationError(s"unrecognized classKey $classKey for UnambigId")
      }
      case _ => deserializationError("unrecognized UnambigId")
    }
  }

  // Tpt
  implicit object TptJ extends JsonFormat[Tpt] {
    def write(v: Tpt) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: TptAnnotate => ("TptAnnotate", v.asInstanceOf[TptAnnotate].toJson)
        case _: TptArray => ("TptArray", v.asInstanceOf[TptArray].toJson)
        case _: TptBoolean => ("TptBoolean", v.asInstanceOf[TptBoolean].toJson)
        case _: TptByName => ("TptByName", v.asInstanceOf[TptByName].toJson)
        case _: TptByte => ("TptByte", v.asInstanceOf[TptByte].toJson)
        case _: TptChar => ("TptChar", v.asInstanceOf[TptChar].toJson)
        case _: TptDouble => ("TptDouble", v.asInstanceOf[TptDouble].toJson)
        case _: TptExistential => ("TptExistential", v.asInstanceOf[TptExistential].toJson)
        case _: TptFloat => ("TptFloat", v.asInstanceOf[TptFloat].toJson)
        case _: TptFunction => ("TptFunction", v.asInstanceOf[TptFunction].toJson)
        case _: TptId => ("TptId", v.asInstanceOf[TptId].toJson)
        case _: TptInt => ("TptInt", v.asInstanceOf[TptInt].toJson)
        case _: TptIntersect => ("TptIntersect", v.asInstanceOf[TptIntersect].toJson)
        case _: TptLong => ("TptLong", v.asInstanceOf[TptLong].toJson)
        case _: TptParameterize => ("TptParameterize", v.asInstanceOf[TptParameterize].toJson)
        case _: TptParameterizeInfix => ("TptParameterizeInfix", v.asInstanceOf[TptParameterizeInfix].toJson)
        case _: TptProject => ("TptProject", v.asInstanceOf[TptProject].toJson)
        case _: TptRefine => ("TptRefine", v.asInstanceOf[TptRefine].toJson)
        case _: TptRepeat => ("TptRepeat", v.asInstanceOf[TptRepeat].toJson)
        case _: TptSelect => ("TptSelect", v.asInstanceOf[TptSelect].toJson)
        case _: TptShort => ("TptShort", v.asInstanceOf[TptShort].toJson)
        case _: TptSingleton => ("TptSingleton", v.asInstanceOf[TptSingleton].toJson)
        case _: TptTuple => ("TptTuple", v.asInstanceOf[TptTuple].toJson)
        case _: TptVoid => ("TptVoid", v.asInstanceOf[TptVoid].toJson)
        case _: TptWildcard => ("TptWildcard", v.asInstanceOf[TptWildcard].toJson)
        case _: TptWildcardExistential => ("TptWildcardExistential", v.asInstanceOf[TptWildcardExistential].toJson)
        case _: TptWith => ("TptWith", v.asInstanceOf[TptWith].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "TptAnnotate" => obj.convertTo[TptAnnotate]
        case "TptArray" => obj.convertTo[TptArray]
        case "TptBoolean" => obj.convertTo[TptBoolean]
        case "TptByName" => obj.convertTo[TptByName]
        case "TptByte" => obj.convertTo[TptByte]
        case "TptChar" => obj.convertTo[TptChar]
        case "TptDouble" => obj.convertTo[TptDouble]
        case "TptExistential" => obj.convertTo[TptExistential]
        case "TptFloat" => obj.convertTo[TptFloat]
        case "TptFunction" => obj.convertTo[TptFunction]
        case "TptId" => obj.convertTo[TptId]
        case "TptInt" => obj.convertTo[TptInt]
        case "TptIntersect" => obj.convertTo[TptIntersect]
        case "TptLong" => obj.convertTo[TptLong]
        case "TptParameterize" => obj.convertTo[TptParameterize]
        case "TptParameterizeInfix" => obj.convertTo[TptParameterizeInfix]
        case "TptProject" => obj.convertTo[TptProject]
        case "TptRefine" => obj.convertTo[TptRefine]
        case "TptRepeat" => obj.convertTo[TptRepeat]
        case "TptSelect" => obj.convertTo[TptSelect]
        case "TptShort" => obj.convertTo[TptShort]
        case "TptSingleton" => obj.convertTo[TptSingleton]
        case "TptTuple" => obj.convertTo[TptTuple]
        case "TptVoid" => obj.convertTo[TptVoid]
        case "TptWildcard" => obj.convertTo[TptWildcard]
        case "TptWildcardExistential" => obj.convertTo[TptWildcardExistential]
        case "TptWith" => obj.convertTo[TptWith]
        case _ => deserializationError(s"unrecognized classKey $classKey for Tpt")
      }
      case _ => deserializationError("unrecognized Tpt")
    }
  }
  implicit val tptAnnotate: JsonFormat[TptAnnotate] = lazyFormat(jsonFormat(TptAnnotate, "tpt", "mods"))
  implicit val tptArray: JsonFormat[TptArray] = lazyFormat(jsonFormat(TptArray, "tpt"))
  implicit val tptBoolean: JsonFormat[TptBoolean] = jsonFormat0(TptBoolean)
  implicit val tptByName: JsonFormat[TptByName] = lazyFormat(jsonFormat(TptByName, "tpt"))
  implicit val tptByte: JsonFormat[TptByte] = jsonFormat0(TptByte)
  implicit val tptChar: JsonFormat[TptChar] = jsonFormat0(TptChar)
  implicit val tptDouble: JsonFormat[TptDouble] = jsonFormat0(TptDouble)
  // TptExistential
  implicit val tptFloat: JsonFormat[TptFloat] = jsonFormat0(TptFloat)
  implicit val tptFunction: JsonFormat[TptFunction] = lazyFormat(jsonFormat(TptFunction, "targs"))
  implicit val tptId: JsonFormat[TptId] = jsonFormat1(TptId)
  implicit val tptInt: JsonFormat[TptInt] = jsonFormat0(TptInt)
  implicit val tptIntersect: JsonFormat[TptIntersect] = lazyFormat(jsonFormat(TptIntersect, "tpts"))
  implicit val tptLong: JsonFormat[TptLong] = jsonFormat0(TptLong)
  implicit val tptParameterize: JsonFormat[TptParameterize] = lazyFormat(jsonFormat(TptParameterize, "fun", "targs"))
  implicit val tptParameterizeInfix: JsonFormat[TptParameterizeInfix] = lazyFormat(jsonFormat(TptParameterizeInfix, "lhs", "op", "rhs"))
  implicit val tptProject: JsonFormat[TptProject] = lazyFormat(jsonFormat(TptProject, "qual", "id"))
  // TptRefine
  implicit val tptRepeat: JsonFormat[TptRepeat] = lazyFormat(jsonFormat(TptRepeat, "tpt"))
  implicit val tptSelect: JsonFormat[TptSelect] = lazyFormat(jsonFormat(TptSelect, "qual", "id"))
  implicit val tptShort: JsonFormat[TptShort] = jsonFormat0(TptShort)
  implicit val tptSingleton: JsonFormat[TptSingleton] = lazyFormat(jsonFormat(TptSingleton, "qual"))
  implicit val tptTuple: JsonFormat[TptTuple] = lazyFormat(jsonFormat(TptTuple, "targs"))
  implicit val tptVoid: JsonFormat[TptVoid] = jsonFormat0(TptVoid)
  implicit val tptWildcard: JsonFormat[TptWildcard] = lazyFormat(jsonFormat(TptWildcard, "lbound", "ubound"))
  implicit val tptWildcardExistential: JsonFormat[TptWildcardExistential] = lazyFormat(jsonFormat(TptWildcardExistential, "ids", "tpt"))
  implicit val tptWith: JsonFormat[TptWith] = lazyFormat(jsonFormat(TptWith, "tpts"))

  // Parent
  implicit object ParentJ extends JsonFormat[Parent] {
    def write(v: Parent) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: ParentExtends => ("ParentExtends", v.asInstanceOf[ParentExtends].toJson)
        case _: ParentImplements => ("ParentImplements", v.asInstanceOf[ParentImplements].toJson)
        case _: Init => ("Init", v.asInstanceOf[Init].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "ParentExtends" => obj.convertTo[ParentExtends]
        case "ParentImplements" => obj.convertTo[ParentImplements]
        case "Init" => obj.convertTo[Init]
        case _ => deserializationError(s"unrecognized classKey $classKey for Parent")
      }
      case _ => deserializationError("unrecognized Parent")
    }
  }
  implicit val parentExtends: JsonFormat[ParentExtends] = lazyFormat(jsonFormat(ParentExtends, "tpt"))
  implicit val parentImplements: JsonFormat[ParentImplements] = lazyFormat(jsonFormat(ParentImplements, "tpt"))
  // Init

  // ModWithin
  implicit object ModWithinJ extends JsonFormat[ModWithin] {
    def write(v: ModWithin) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: ModPrivateWithin => ("ModPrivateWithin", v.asInstanceOf[ModPrivateWithin].toJson)
        case _: ModProtectedWithin => ("ModProtectedWithin", v.asInstanceOf[ModProtectedWithin].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "ModPrivateWithin" => obj.convertTo[ModPrivateWithin]
        case "ModProtectedWithin" => obj.convertTo[ModProtectedWithin]
        case _ => deserializationError(s"unrecognized classKey $classKey for ModWithin")
      }
      case _ => deserializationError("unrecognized ModWithin")
    }
  }
  implicit val modPrivateWithin: JsonFormat[ModPrivateWithin] = lazyFormat(jsonFormat(ModPrivateWithin, "id"))
  implicit val modProtectedWithin: JsonFormat[ModProtectedWithin] = lazyFormat(jsonFormat(ModProtectedWithin, "id"))

  // ModAccess
  implicit object ModAccessJ extends JsonFormat[ModAccess] {
    def write(v: ModAccess) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: ModPrivate => ("ModPrivate", v.asInstanceOf[ModPrivate].toJson)
        case _: ModPrivateThis => ("ModPrivateThis", v.asInstanceOf[ModPrivateThis].toJson)
        case _: ModProtected => ("ModProtected", v.asInstanceOf[ModProtected].toJson)
        case _: ModProtectedThis => ("ModProtectedThis", v.asInstanceOf[ModProtectedThis].toJson)
        case _: ModPublic => ("ModPublic", v.asInstanceOf[ModPublic].toJson)
        case _: ModWithin => ("ModWithin", v.asInstanceOf[ModWithin].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "ModPrivate" => obj.convertTo[ModPrivate]
        case "ModPrivateThis" => obj.convertTo[ModPrivateThis]
        case "ModProtected" => obj.convertTo[ModProtected]
        case "ModProtectedThis" => obj.convertTo[ModProtectedThis]
        case "ModPublic" => obj.convertTo[ModPublic]
        case "ModWithin" => obj.convertTo[ModWithin]
        case _ => deserializationError(s"unrecognized classKey $classKey for ModAccess")
      }
      case _ => deserializationError("unrecognized ModAccess")
    }
  }
  implicit val modPrivate: JsonFormat[ModPrivate] = jsonFormat0(ModPrivate)
  implicit val modPrivateThis: JsonFormat[ModPrivateThis] = jsonFormat0(ModPrivateThis)
  implicit val modProtected: JsonFormat[ModProtected] = jsonFormat0(ModProtected)
  implicit val modProtectedThis: JsonFormat[ModProtectedThis] = jsonFormat0(ModProtectedThis)
  implicit val modPublic: JsonFormat[ModPublic] = jsonFormat0(ModPublic)

  // Mod
  implicit object ModJ extends JsonFormat[Mod] {
    def write(v: Mod) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: ModAbstract => ("ModAbstract", v.asInstanceOf[ModAbstract].toJson)
        case _: ModAccess => ("ModAccess", v.asInstanceOf[ModAccess].toJson)
        case _: ModAnnotation => ("ModAnnotation", v.asInstanceOf[ModAnnotation].toJson)
        case _: ModAnnotationInterface => ("ModAnnotationInterface", v.asInstanceOf[ModAnnotationInterface].toJson)
        case _: ModCase => ("ModCase", v.asInstanceOf[ModCase].toJson)
        case _: ModClass => ("ModClass", v.asInstanceOf[ModClass].toJson)
        case _: ModContravariant => ("ModContravariant", v.asInstanceOf[ModContravariant].toJson)
        case _: ModCovariant => ("ModCovariant", v.asInstanceOf[ModCovariant].toJson)
        case _: ModDefault => ("ModDefault", v.asInstanceOf[ModDefault].toJson)
        case _: ModDims => ("ModDims", v.asInstanceOf[ModDims].toJson)
        case _: ModEnum => ("ModEnum", v.asInstanceOf[ModEnum].toJson)
        case _: ModFinal => ("ModFinal", v.asInstanceOf[ModFinal].toJson)
        case _: ModImplicit => ("ModImplicit", v.asInstanceOf[ModImplicit].toJson)
        case _: ModInterface => ("ModInterface", v.asInstanceOf[ModInterface].toJson)
        case _: ModLazy => ("ModLazy", v.asInstanceOf[ModLazy].toJson)
        case _: ModNative => ("ModNative", v.asInstanceOf[ModNative].toJson)
        case _: ModOverride => ("ModOverride", v.asInstanceOf[ModOverride].toJson)
        case _: ModSealed => ("ModSealed", v.asInstanceOf[ModSealed].toJson)
        case _: ModStatic => ("ModStatic", v.asInstanceOf[ModStatic].toJson)
        case _: ModStrictfp => ("ModStrictfp", v.asInstanceOf[ModStrictfp].toJson)
        case _: ModSynchronized => ("ModSynchronized", v.asInstanceOf[ModSynchronized].toJson)
        case _: ModThrows => ("ModThrows", v.asInstanceOf[ModThrows].toJson)
        case _: ModTrait => ("ModTrait", v.asInstanceOf[ModTrait].toJson)
        case _: ModTransient => ("ModTransient", v.asInstanceOf[ModTransient].toJson)
        case _: ModVal => ("ModVal", v.asInstanceOf[ModVal].toJson)
        case _: ModVar => ("ModVar", v.asInstanceOf[ModVar].toJson)
        case _: ModVolatile => ("ModVolatile", v.asInstanceOf[ModVolatile].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "ModAbstract" => obj.convertTo[ModAbstract]
        case "ModAccess" => obj.convertTo[ModAccess]
        case "ModAnnotation" => obj.convertTo[ModAnnotation]
        case "ModAnnotationInterface" => obj.convertTo[ModAnnotationInterface]
        case "ModCase" => obj.convertTo[ModCase]
        case "ModClass" => obj.convertTo[ModClass]
        case "ModContravariant" => obj.convertTo[ModContravariant]
        case "ModCovariant" => obj.convertTo[ModCovariant]
        case "ModDefault" => obj.convertTo[ModDefault]
        case "ModDims" => obj.convertTo[ModDims]
        case "ModEnum" => obj.convertTo[ModEnum]
        case "ModFinal" => obj.convertTo[ModFinal]
        case "ModImplicit" => obj.convertTo[ModImplicit]
        case "ModInterface" => obj.convertTo[ModInterface]
        case "ModLazy" => obj.convertTo[ModLazy]
        case "ModNative" => obj.convertTo[ModNative]
        case "ModOverride" => obj.convertTo[ModOverride]
        case "ModSealed" => obj.convertTo[ModSealed]
        case "ModStatic" => obj.convertTo[ModStatic]
        case "ModStrictfp" => obj.convertTo[ModStrictfp]
        case "ModSynchronized" => obj.convertTo[ModSynchronized]
        case "ModThrows" => obj.convertTo[ModThrows]
        case "ModTrait" => obj.convertTo[ModTrait]
        case "ModTransient" => obj.convertTo[ModTransient]
        case "ModVal" => obj.convertTo[ModVal]
        case "ModVar" => obj.convertTo[ModVar]
        case "ModVolatile" => obj.convertTo[ModVolatile]
        case _ => deserializationError(s"unrecognized classKey $classKey for Mod")
      }
      case _ => deserializationError("unrecognized Mod")
    }
  }
  implicit val modAbstract: JsonFormat[ModAbstract] = jsonFormat0(ModAbstract)
  implicit val modAnnotation: JsonFormat[ModAnnotation] = lazyFormat(jsonFormat(ModAnnotation, "init"))
  implicit val modAnnotationInterface: JsonFormat[ModAnnotationInterface] = jsonFormat0(ModAnnotationInterface)
  implicit val modCase: JsonFormat[ModCase] = jsonFormat0(ModCase)
  implicit val modClass: JsonFormat[ModClass] = jsonFormat0(ModClass)
  implicit val modContravariant: JsonFormat[ModContravariant] = jsonFormat0(ModContravariant)
  implicit val modCovariant: JsonFormat[ModCovariant] = jsonFormat0(ModCovariant)
  implicit val modDefault: JsonFormat[ModDefault] = jsonFormat0(ModDefault)
  implicit val modDims: JsonFormat[ModDims] = jsonFormat0(ModDims)
  implicit val modEnum: JsonFormat[ModEnum] = jsonFormat0(ModEnum)
  implicit val modFinal: JsonFormat[ModFinal] = jsonFormat0(ModFinal)
  implicit val modImplicit: JsonFormat[ModImplicit] = jsonFormat0(ModImplicit)
  implicit val modInterface: JsonFormat[ModInterface] = jsonFormat0(ModInterface)
  implicit val modLazy: JsonFormat[ModLazy] = jsonFormat0(ModLazy)
  implicit val modNative: JsonFormat[ModNative] = jsonFormat0(ModNative)
  implicit val modOverride: JsonFormat[ModOverride] = jsonFormat0(ModOverride)
  implicit val mods: JsonFormat[Mods] = lazyFormat(jsonFormat(Mods, "trees"))
  implicit val modSealed: JsonFormat[ModSealed] = jsonFormat0(ModSealed)
  implicit val modStatic: JsonFormat[ModStatic] = jsonFormat0(ModStatic)
  implicit val modStrictfp: JsonFormat[ModStrictfp] = jsonFormat0(ModStrictfp)
  implicit val modSynchronized: JsonFormat[ModSynchronized] = jsonFormat0(ModSynchronized)
  implicit val modThrows: JsonFormat[ModThrows] = lazyFormat(jsonFormat(ModThrows, "tpts"))
  implicit val modTrait: JsonFormat[ModTrait] = jsonFormat0(ModTrait)
  implicit val modTransient: JsonFormat[ModTransient] = jsonFormat0(ModTransient)
  implicit val modVal: JsonFormat[ModVal] = jsonFormat0(ModVal)
  implicit val modVar: JsonFormat[ModVar] = jsonFormat0(ModVar)
  implicit val modVolatile: JsonFormat[ModVolatile] = jsonFormat0(ModVolatile)

  // DefnDef
  implicit val primaryCtor: JsonFormat[PrimaryCtor] = lazyFormat(jsonFormat(
    PrimaryCtor, "mods", "paramss"))
  implicit object DefnDefJ extends JsonFormat[DefnDef] {
    def write(v: DefnDef) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: DefnCtor => ("DefnCtor", v.asInstanceOf[DefnCtor].toJson)
        case _: DefnMacro => ("DefnMacro", v.asInstanceOf[DefnMacro].toJson)
        case _: DefnMethod => ("DefnMethod", v.asInstanceOf[DefnMethod].toJson)
        case _: DefnProcedure => ("DefnProcedure", v.asInstanceOf[DefnProcedure].toJson)
        case _: PrimaryCtor => ("PrimaryCtor", v.asInstanceOf[PrimaryCtor].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "DefnCtor" => obj.convertTo[DefnCtor]
        case "DefnMacro" => obj.convertTo[DefnMacro]
        case "DefnMethod" => obj.convertTo[DefnMethod]
        case "DefnProcedure" => obj.convertTo[DefnProcedure]
        case "PrimaryCtor" => obj.convertTo[PrimaryCtor]
        case _ => deserializationError(s"unrecognized classKey $classKey for DefnDef")
      }
      case _ => deserializationError(s"unrecognized DefnDef")
    }
  }

  // DefnTemplate
  implicit object DefnTemplateJ extends JsonFormat[DefnTemplate] {
    def write(v: DefnTemplate) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: DefnClass => ("DefnClass", v.asInstanceOf[DefnClass].toJson)
        case _: DefnObject => ("DefnObject", v.asInstanceOf[DefnObject].toJson)
        case _: DefnPackageObject => ("DefnPackageObject", v.asInstanceOf[DefnPackageObject].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "DefnClass" => obj.convertTo[DefnClass]
        case "DefnObject" => obj.convertTo[DefnObject]
        case "DefnPackageObject" => obj.convertTo[DefnPackageObject]
        case _ => deserializationError(s"unrecognized classKey $classKey for DefnTemplate")
      }
      case _ => deserializationError(s"unrecognized DefnTemplate")
    }
  }

  // Enumerator
  implicit object EnumeratorJ extends JsonFormat[Enumerator] {
    def write(v: Enumerator) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: EnumeratorGenerator => ("EnumeratorGenerator", v.asInstanceOf[EnumeratorGenerator].toJson)
        case _: EnumeratorGuard => ("EnumeratorGuard", v.asInstanceOf[EnumeratorGuard].toJson)
        case _: EnumeratorVal => ("EnumeratorVal", v.asInstanceOf[EnumeratorVal].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "EnumeratorGenerator" => obj.convertTo[EnumeratorGenerator]
        case "EnumeratorGuard" => obj.convertTo[EnumeratorGuard]
        case "EnumeratorVal" => obj.convertTo[EnumeratorVal]
        case _ => deserializationError(s"unrecognized classKey $classKey for Enumerator")
      }
      case _ => deserializationError(s"unrecognized Enumerator")
    }
  }

  // Importee
  implicit object ImporteeJ extends JsonFormat[Importee] {
    def write(v: Importee) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: ImporteeName => ("ImporteeName", v.asInstanceOf[ImporteeName].toJson)
        case _: ImporteeRename => ("ImporteeRename", v.asInstanceOf[ImporteeRename].toJson)
        case _: ImporteeUnimport => ("ImporteeUnimport", v.asInstanceOf[ImporteeUnimport].toJson)
        case _: ImporteeWildcard => ("ImporteeWildcard", v.asInstanceOf[ImporteeWildcard])
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "ImporteeName" => obj.convertTo[ImporteeName]
        case "ImporteeRename" => obj.convertTo[ImporteeRename]
        case "ImporteeUnimport" => obj.convertTo[ImporteeUnimport]
        case "ImporteeWildcard" => obj.convertTo[ImporteeWildcard]
        case _ => deserializationError(s"unrecognized classKey $classKey for Importee")
      }
      case _ => deserializationError(s"unrecognized Importee")
    }
  }
  implicit val importeeName: JsonFormat[ImporteeName] = lazyFormat(jsonFormat(ImporteeName, "id"))
  implicit val importeeRename: JsonFormat[ImporteeRename] = lazyFormat(jsonFormat(ImporteeRename, "from", "to"))
  implicit val importeeUnimport: JsonFormat[ImporteeUnimport] = lazyFormat(jsonFormat(ImporteeUnimport, "id"))
  implicit val importeeWildcard: JsonFormat[ImporteeWildcard] = jsonFormat0(ImporteeWildcard)

  // Pat
  // Need to case match against Any.
  implicit object PatLitJ extends JsonFormat[PatLit] {
    def write(v: PatLit) = {
      val (classKey: String, convertedJson: JsValue) = v.value match {
        case x: Unit => ("Unit", x.asInstanceOf[Unit].toJson)
        case x: Char => ("Char", x.asInstanceOf[Char].toJson)
        case x: Int => ("Int", x.asInstanceOf[Int].toJson)
        case x: Long => ("Long", x.asInstanceOf[Long].toJson)
        case x: Float => ("Float", x.asInstanceOf[Float].toJson)
        case x: Double => ("Double", x.asInstanceOf[Double].toJson)
        case x: String => ("String", x.asInstanceOf[String].toJson)
        case x: Boolean => ("Boolean", x.asInstanceOf[Boolean].toJson)
        case null => ("null", "null".parseJson)
        case x: StdlibSymbol => ("StdlibSymbol", x.asInstanceOf[StdlibSymbol].toJson)
        case _ => serializationError(s"unrecognized value ${v.value} for PatLit")
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = {
      val litValue = o match {
        case JsArray(Vector(JsString(classKey), obj)) => classKey match {
          case "Unit" => obj.convertTo[Unit]
          case "Char" => obj.convertTo[Char]
          case "Int" => obj.convertTo[Int]
          case "Long" => obj.convertTo[Long]
          case "Float" => obj.convertTo[Float]
          case "Double" => obj.convertTo[Double]
          case "String" => obj.convertTo[String]
          case "Boolean" => obj.convertTo[Boolean]
          case "null" => null
          case "StdlibSymbol" => obj.convertTo[StdlibSymbol]
          case _ => deserializationError(s"unrecognized classKey $classKey for PatLit")
        }
        case _ => deserializationError("unrecognized PatLit")
      }
      PatLit(litValue)
    }
  }
  implicit object PatJ extends JsonFormat[Pat] {
    def write(v: Pat) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: PatAlternative => ("PatAlternative", v.asInstanceOf[PatAlternative].toJson)
        case _: PatBind => ("PatBind", v.asInstanceOf[PatBind].toJson)
        case _: PatExtract => ("PatExtract", v.asInstanceOf[PatExtract].toJson)
        case _: PatExtractInfix => ("PatExtractInfix", v.asInstanceOf[PatExtractInfix].toJson)
        case _: PatId => ("PatId", v.asInstanceOf[PatId].toJson)
        case _: PatInterpolate => ("PatInterpolate", v.asInstanceOf[PatInterpolate].toJson)
        case _: PatLit => ("PatLit", v.asInstanceOf[PatLit].toJson)
        case _: PatRepeat => ("PatRepeat", v.asInstanceOf[PatRepeat].toJson)
        case _: PatSelect => ("PatSelect", v.asInstanceOf[PatSelect].toJson)
        case _: PatTuple => ("PatTuple", v.asInstanceOf[PatTuple].toJson)
        case _: PatVar => ("PatVar", v.asInstanceOf[PatVar].toJson)
        case _: PatXml => ("PatXml", v.asInstanceOf[PatXml].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "PatAlternative" => obj.convertTo[PatAlternative]
        case "PatBind" =>  obj.convertTo[PatBind]
        case "PatExtract" =>  obj.convertTo[PatExtract]
        case "PatExtractInfix" =>  obj.convertTo[PatExtractInfix]
        case "PatId" =>  obj.convertTo[PatId]
        case "PatInterpolate" =>  obj.convertTo[PatInterpolate]
        case "PatLit" =>  obj.convertTo[PatLit]
        case "PatRepeat" =>  obj.convertTo[PatRepeat]
        case "PatSelect" =>  obj.convertTo[PatSelect]
        case "PatTuple" =>  obj.convertTo[PatTuple]
        case "PatVar" =>  obj.convertTo[PatVar]
        case "PatXml" =>  obj.convertTo[PatXml]
        case _ => deserializationError(s"unrecognized classKey $classKey for Pat")
      }
      case _ => deserializationError("unrecognized Pat")
    }
  }
  implicit val patAlternative: JsonFormat[PatAlternative] = lazyFormat(jsonFormat(PatAlternative, "pats"))
  implicit val patBind: JsonFormat[PatBind] = lazyFormat(jsonFormat(PatBind, "pats"))
  implicit val patExtract: JsonFormat[PatExtract] = lazyFormat(jsonFormat(
    PatExtract, "fun", "targs", "args"))
  implicit val patExtractInfix: JsonFormat[PatExtractInfix] = lazyFormat(jsonFormat(
    PatExtractInfix, "lhs", "op", "rhs"))
  implicit val patId: JsonFormat[PatId] = jsonFormat1(PatId)
  implicit val patInterpolate: JsonFormat[PatInterpolate] = lazyFormat(jsonFormat(
    PatInterpolate, "id", "parts", "args"))
  implicit val patRepeat: JsonFormat[PatRepeat] = lazyFormat(jsonFormat(PatRepeat, "pat"))
  implicit val patSelect: JsonFormat[PatSelect] = lazyFormat(jsonFormat(PatSelect, "qual", "id"))
  implicit val patTuple: JsonFormat[PatTuple] = lazyFormat(jsonFormat(PatTuple, "args"))
  implicit val patVar: JsonFormat[PatVar] = lazyFormat(jsonFormat(PatVar, "mods", "id", "tpt"))
  implicit val patXml: JsonFormat[PatXml] = jsonFormat1(PatXml)

  // Term
  // Need to case match against Any.
  implicit object TermLitJ extends JsonFormat[TermLit] {
    def write(v: TermLit) = {
      val (classKey: String, convertedJson: JsValue) = v.value match {
        case x: Unit => ("Unit", x.asInstanceOf[Unit].toJson)
        case x: Char => ("Char", x.asInstanceOf[Char].toJson)
        case x: Int => ("Int", x.asInstanceOf[Int].toJson)
        case x: Long => ("Long", x.asInstanceOf[Long].toJson)
        case x: Float => ("Float", x.asInstanceOf[Float].toJson)
        case x: Double => ("Double", x.asInstanceOf[Double].toJson)
        case x: String => ("String", x.asInstanceOf[String].toJson)
        case x: Boolean => ("Boolean", x.asInstanceOf[Boolean].toJson)
        case null => ("null", "null".parseJson)
        case x: StdlibSymbol => ("StdlibSymbol", x.asInstanceOf[StdlibSymbol].toJson)
        case _ => serializationError(s"unrecognized value ${v.value} for TermLit")
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = {
      val litValue = o match {
        case JsArray(Vector(JsString(classKey), obj)) => classKey match {
          case "Unit" => obj.convertTo[Unit]
          case "Char" => obj.convertTo[Char]
          case "Int" => obj.convertTo[Int]
          case "Long" => obj.convertTo[Long]
          case "Float" => obj.convertTo[Float]
          case "Double" => obj.convertTo[Double]
          case "String" => obj.convertTo[String]
          case "Boolean" => obj.convertTo[Boolean]
          case "null" => null
          case "StdlibSymbol" => obj.convertTo[StdlibSymbol]
          case _ => deserializationError(s"unrecognized classKey $classKey for TermLit")
        }
        case _ => deserializationError("unrecognized TermLit")
      }
      TermLit(litValue)
    }
  }
  implicit object TermJ extends JsonFormat[Term] {
    def write(v: Term) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: TermIf => ("TermIf", v.asInstanceOf[TermIf].toJson)
        case _: TermInterpolate => ("TermInterpolate", v.asInstanceOf[TermInterpolate].toJson)
        case _: TermLit => ("TermLit", v.asInstanceOf[TermLit].toJson)
        case _: TermMatch => ("TermMatch", v.asInstanceOf[TermMatch].toJson)
        case _: TermNew => ("TermNew", v.asInstanceOf[TermNew].toJson)
        case _: TermNewAnonymous => ("TermNewAnonymous", v.asInstanceOf[TermNewAnonymous].toJson)
        case _: TermPartialFunction => ("TermPartialFunction", v.asInstanceOf[TermPartialFunction].toJson)
        case _: TermRepeat => ("TermRepeat", v.asInstanceOf[TermRepeat].toJson)
        case _: TermReturn => ("TermReturn", v.asInstanceOf[TermReturn].toJson)
        case _: TermSelect => ("TermSelect", v.asInstanceOf[TermSelect].toJson)
        case _: TermStub => ("TermStub", v.asInstanceOf[TermStub].toJson)
        case _: TermSuper => ("TermSuper", v.asInstanceOf[TermSuper].toJson)
        case _: TermThis => ("TermThis", v.asInstanceOf[TermThis].toJson)
        case _: TermThrow => ("TermThrow", v.asInstanceOf[TermThrow].toJson)
        case _: TermTry => ("TermTry", v.asInstanceOf[TermTry].toJson)
        case _: TermTryWithHandler => ("TermTryWithHandler", v.asInstanceOf[TermTryWithHandler].toJson)
        case _: TermTuple => ("TermTuple", v.asInstanceOf[TermTuple].toJson)
        case _: TermWhile => ("TermWhile", v.asInstanceOf[TermWhile].toJson)
        case _: TermWildcard => ("TermWildcard", v.asInstanceOf[TermWildcard].toJson)
        case _: TermWildcardFunction => ("TermWildcardFunction", v.asInstanceOf[TermWildcardFunction].toJson)
        case _: TermXml => ("TermXml", v.asInstanceOf[TermXml].toJson)
        case _: Init => ("Init", v.asInstanceOf[Init].toJson)
        case _: TermAnnotate => ("TermAnnotate", v.asInstanceOf[TermAnnotate].toJson)
        case _: TermApply => ("TermApply", v.asInstanceOf[TermApply].toJson)
        case _: TermApplyInfix => ("TermApplyInfix", v.asInstanceOf[TermApplyInfix].toJson)
        case _: TermApplyPostfix => ("TermApplyPostfix", v.asInstanceOf[TermApplyPostfix].toJson)
        case _: TermApplyPrefix => ("TermApplyPrefix", v.asInstanceOf[TermApplyPrefix].toJson)
        case _: TermApplyType => ("TermApplyType", v.asInstanceOf[TermApplyType].toJson)
        case _: TermAscribe => ("TermAscribe", v.asInstanceOf[TermAscribe].toJson)
        case _: TermAssign => ("TermAssign", v.asInstanceOf[TermAssign].toJson)
        case _: TermBlock => ("TermBlock", v.asInstanceOf[TermBlock].toJson)
        case _: TermDo => ("TermDo", v.asInstanceOf[TermDo].toJson)
        case _: TermEta => ("TermEta", v.asInstanceOf[TermEta].toJson)
        case _: TermFor => ("TermFor", v.asInstanceOf[TermFor].toJson)
        case _: TermForYield => ("TermForYield", v.asInstanceOf[TermForYield].toJson)
        case _: TermFunction => ("TermFunction", v.asInstanceOf[TermFunction].toJson)
        case _: TermId => ("TermId", v.asInstanceOf[TermId].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "TermIf" => obj.convertTo[TermIf]
        case "TermInterpolate" => obj.convertTo[TermInterpolate]
        case "TermLit" => obj.convertTo[TermLit]
        case "TermMatch" => obj.convertTo[TermMatch]
        case "TermNew" => obj.convertTo[TermNew]
        case "TermNewAnonymous" => obj.convertTo[TermNewAnonymous]
        case "TermPartialFunction" => obj.convertTo[TermPartialFunction]
        case "TermRepeat" => obj.convertTo[TermRepeat]
        case "TermReturn" => obj.convertTo[TermReturn]
        case "TermSelect" => obj.convertTo[TermSelect]
        case "TermStub" => obj.convertTo[TermStub]
        case "TermSuper" => obj.convertTo[TermSuper]
        case "TermThis" => obj.convertTo[TermThis]
        case "TermThrow" => obj.convertTo[TermThrow]
        case "TermTry" => obj.convertTo[TermTry]
        case "TermTryWithHandler" => obj.convertTo[TermTryWithHandler]
        case "TermTuple" => obj.convertTo[TermTuple]
        case "TermWhile" => obj.convertTo[TermWhile]
        case "TermWildcard" => obj.convertTo[TermWildcard]
        case "TermWildcardFunction" => obj.convertTo[TermWildcardFunction]
        case "TermXml" => obj.convertTo[TermXml]
        case "Init" => obj.convertTo[Init]
        case "TermAnnotate" => obj.convertTo[TermAnnotate]
        case "TermApply" => obj.convertTo[TermApply]
        case "TermApplyInfix" => obj.convertTo[TermApplyInfix]
        case "TermApplyPostfix" => obj.convertTo[TermApplyPostfix]
        case "TermApplyPrefix" => obj.convertTo[TermApplyPrefix]
        case "TermApplyType" => obj.convertTo[TermApplyType]
        case "TermAscribe" => obj.convertTo[TermAscribe]
        case "TermAssign" => obj.convertTo[TermAssign]
        case "TermBlock" => obj.convertTo[TermBlock]
        case "TermDo" => obj.convertTo[TermDo]
        case "TermEta" => obj.convertTo[TermEta]
        case "TermFor" => obj.convertTo[TermFor]
        case "TermForYield" => obj.convertTo[TermForYield]
        case "TermFunction" => obj.convertTo[TermFunction]
        case "TermId" => obj.convertTo[TermId]
        case _ => deserializationError(s"unrecognized classKey $classKey for Term")
      }
      case _ => deserializationError("unrecognized Term")
    }
  }
  implicit val termAnnotate: JsonFormat[TermAnnotate] = lazyFormat(jsonFormat(TermAnnotate, "fun", "mods"))
  implicit val termApply: JsonFormat[TermApply] = lazyFormat(jsonFormat(TermApply, "fun", "args"))
  implicit val termApplyInfix: JsonFormat[TermApplyInfix] = lazyFormat(jsonFormat(TermApplyInfix, "lhs", "op", "targs", "args"))
  implicit val termApplyPostfix: JsonFormat[TermApplyPostfix] = lazyFormat(jsonFormat(TermApplyPostfix, "arg", "op"))
  implicit val termApplyPrefix: JsonFormat[TermApplyPrefix] = lazyFormat(jsonFormat(TermApplyPrefix, "op", "arg"))
  implicit val termApplyType: JsonFormat[TermApplyType] = lazyFormat(jsonFormat(TermApplyType, "fun", "targs"))
  implicit val termAscribe: JsonFormat[TermAscribe] = lazyFormat(jsonFormat(TermAscribe, "term", "tpt"))
  implicit val termAssign: JsonFormat[TermAssign] = lazyFormat(jsonFormat(TermAssign, "lhs", "rhs"))
  implicit val termDo: JsonFormat[TermDo] = lazyFormat(jsonFormat(TermDo, "body", "cond"))
  implicit val termEta: JsonFormat[TermEta] = lazyFormat(jsonFormat(TermEta, "term"))
  implicit val termFor: JsonFormat[TermFor] = lazyFormat(jsonFormat(TermFor, "enums", "body"))
  implicit val termForYield: JsonFormat[TermForYield] = lazyFormat(jsonFormat(TermForYield, "enums", "body"))
  implicit val termFunction: JsonFormat[TermFunction] = lazyFormat(jsonFormat(TermFunction, "params", "body"))
  implicit val termId: JsonFormat[TermId] = jsonFormat1(TermId)
  implicit val termIf: JsonFormat[TermIf] = lazyFormat(jsonFormat(TermIf, "cond", "thenp", "elsep"))
  implicit val termInterpolate: JsonFormat[TermInterpolate] = lazyFormat(jsonFormat(TermInterpolate, "id", "parts", "args"))
  // Extras needing Term
  implicit val init: JsonFormat[Init] = lazyFormat(jsonFormat(Init, "tpt", "argss"))
  implicit val defnCtor: JsonFormat[DefnCtor] = lazyFormat(jsonFormat(
    DefnCtor, "mods", "id", "paramss", "rhs"))
  implicit val defnMacro: JsonFormat[DefnMacro] = lazyFormat(jsonFormat(
    DefnMacro, "mods", "id", "tparams", "paramss", "ret", "rhs"))
  implicit val defnMethod: JsonFormat[DefnMethod] = lazyFormat(jsonFormat(
    DefnMethod, "mods", "id", "tparams", "paramss", "ret", "rhs"))
  implicit val defnProcedure: JsonFormat[DefnProcedure] = lazyFormat(jsonFormat(
    DefnProcedure, "mods", "id", "tparams", "paramss", "rhs"))
  implicit val param: JsonFormat[Param] = lazyFormat(jsonFormat(Param, "mods", "id", "tgt", "rhs"))
  implicit val enumeratorGenerator: JsonFormat[EnumeratorGenerator] = lazyFormat(jsonFormat(EnumeratorGenerator, "pat", "rhs"))
  implicit val enumeratorGuard: JsonFormat[EnumeratorGuard] = lazyFormat(jsonFormat(EnumeratorGuard, "cond"))
  implicit val enumeratorVal: JsonFormat[EnumeratorVal] = lazyFormat(jsonFormat(EnumeratorVal, "pat", "rhs"))
  // Term implementors
  implicit val termMatch: JsonFormat[TermMatch] = lazyFormat(jsonFormat(TermMatch, "term", "cases"))
  implicit val termNew: JsonFormat[TermNew] = lazyFormat(jsonFormat(TermNew, "init"))
  implicit val termPartialFunction: JsonFormat[TermPartialFunction] = lazyFormat(jsonFormat(TermPartialFunction, "cases"))
  implicit val termRepeat: JsonFormat[TermRepeat] = lazyFormat(jsonFormat(TermRepeat, "term"))
  implicit val termReturn: JsonFormat[TermReturn] = lazyFormat(jsonFormat(TermReturn, "term"))
  implicit val termSelect: JsonFormat[TermSelect] = lazyFormat(jsonFormat(TermSelect, "qual", "id"))
  implicit val termStub: JsonFormat[TermStub] = jsonFormat0(TermStub)
  implicit val termSuper: JsonFormat[TermSuper] = lazyFormat(jsonFormat(TermSuper, "qual", "mix"))
  implicit val termThis: JsonFormat[TermThis] = lazyFormat(jsonFormat(TermThis, "qual"))
  implicit val termThrow: JsonFormat[TermThrow] = lazyFormat(jsonFormat(TermThrow, "term"))
  implicit val termTry: JsonFormat[TermTry] = lazyFormat(jsonFormat(TermTry, "term", "catchp", "finallyp"))
  implicit val termTryWithHandler: JsonFormat[TermTryWithHandler] = lazyFormat(jsonFormat(TermTryWithHandler, "term", "catchp", "finallyp"))
  implicit val termTuple: JsonFormat[TermTuple] = lazyFormat(jsonFormat(TermTuple, "args"))
  implicit val termWhile: JsonFormat[TermWhile] = lazyFormat(jsonFormat(TermWhile, "cond", "body"))
  implicit val termWildcard: JsonFormat[TermWildcard] = jsonFormat0(TermWildcard)
  implicit val termWildcardFunction: JsonFormat[TermWildcardFunction] = lazyFormat(jsonFormat(TermWildcardFunction, "ids", "body"))
  implicit val termXml: JsonFormat[TermXml] = jsonFormat1(TermXml)

  implicit val typeParam: JsonFormat[TypeParam] = lazyFormat(jsonFormat(
    TypeParam, "mods", "id", "tparams", "lbound", "ubound", "vbounds", "cbounds"))

  // Stat
  implicit object StatJ extends JsonFormat[Stat] {
    def write(v: Stat) = {
      val (classKey: String, convertedJson: JsValue) = v match {
        case _: DefnConstant => ("DefnConstant", v.asInstanceOf[DefnConstant].toJson)
        case _: DefnDef => ("DefnDef", v.asInstanceOf[DefnDef].toJson)
        case _: DefnField => ("DefnField", v.asInstanceOf[DefnField].toJson)
        case _: DefnPackage => ("DefnPackage", v.asInstanceOf[DefnPackage].toJson)
        case _: DefnPat => ("DefnPat", v.asInstanceOf[DefnPat].toJson)
        case _: DefnTemplate => ("DefnTemplate", v.asInstanceOf[DefnTemplate].toJson)
        case _: DefnType => ("DefnType", v.asInstanceOf[DefnType].toJson)
        case _: Import => ("Import", v.asInstanceOf[Import].toJson)
        case _: Self => ("Self", v.asInstanceOf[Self].toJson)
        case _: Term => ("Term", v.asInstanceOf[Term].toJson)
      }
      JsArray(JsString(classKey), convertedJson)
    }
    def read(o: JsValue) = o match {
      case JsArray(Vector(JsString(classKey), obj)) => classKey match {
        case "DefnConstant" => obj.convertTo[DefnConstant]
        case "DefnDef" => obj.convertTo[DefnDef]
        case "DefnField" => obj.convertTo[DefnField]
        case "DefnPackage" => obj.convertTo[DefnPackage]
        case "DefnPat" => obj.convertTo[DefnPat]
        case "DefnTemplate" => obj.convertTo[DefnTemplate]
        case "DefnType" => obj.convertTo[DefnType]
        case "Import" => obj.convertTo[Import]
        case "Self" => obj.convertTo[Self]
        case "Term" => obj.convertTo[Term]
        case _ => deserializationError(s"unrecognized classKey $classKey for Stat")
      }
      case _ => deserializationError("unrecognized Stat")
    }
  }
  // Extras needing Stat
  implicit val tptExistential: JsonFormat[TptExistential] = lazyFormat(jsonFormat(TptExistential, "tpt", "stats"))
  implicit val tptRefine: JsonFormat[TptRefine] = lazyFormat(jsonFormat(TptRefine, "tpt", "stats"))
  implicit val defnClass: JsonFormat[DefnClass] = lazyFormat(jsonFormat(
    DefnClass, "mods", "id", "tparams", "primaryCtor", "earlies", "parents", "self", "stats"))
  implicit val defnObject: JsonFormat[DefnObject] = lazyFormat(jsonFormat(
    DefnObject, "mods", "id", "earlies", "inits", "self", "stats"))
  implicit val defnPackageObject: JsonFormat[DefnPackageObject] = lazyFormat(jsonFormat(
    DefnPackageObject, "mods", "id", "earlies", "inits", "self", "stats"))
  implicit val termBlock: JsonFormat[TermBlock] = lazyFormat(jsonFormat(TermBlock, "stats"))
  implicit val termNewAnonymous: JsonFormat[TermNewAnonymous] = lazyFormat(jsonFormat(TermNewAnonymous, "earlies", "inits", "self", "stats"))
  // Stat implementors
  implicit val defnConstant: JsonFormat[DefnConstant] = lazyFormat(jsonFormat(DefnConstant, "mods", "id"))
  implicit val defnField: JsonFormat[DefnField] = lazyFormat(jsonFormat(DefnField, "mods", "id", "tpt", "rhs"))
  implicit val defnPackage: JsonFormat[DefnPackage] = lazyFormat(jsonFormat(DefnPackage, "mods", "pid", "stats"))
  implicit val defnPat: JsonFormat[DefnPat] = lazyFormat(jsonFormat(DefnPat, "mods", "pats", "tpt", "rhs"))
  implicit val defnType: JsonFormat[DefnType] = lazyFormat(jsonFormat(DefnType, "mods", "id", "tparams", "lo", "hi", "rhs"))
  implicit val importJ: JsonFormat[Import] = lazyFormat(jsonFormat(Import, "importers"))
  implicit val self: JsonFormat[Self] = lazyFormat(jsonFormat(Self, "id", "tpt"))

  implicit val caseJ: JsonFormat[Case] = lazyFormat(jsonFormat(Case, "pat", "cond", "stats"))
  implicit val importer: JsonFormat[Importer] = lazyFormat(jsonFormat(Importer, "mods", "qual", "importees"))
  implicit val source: RootJsonFormat[Source] = jsonFormat1(Source)
}
