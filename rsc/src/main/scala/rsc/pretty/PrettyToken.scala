// Copyright (c) 2017-2018 Twitter, Inc.
// Licensed under the Apache License, Version 2.0 (see LICENSE.md).
package rsc.pretty

import rsc.lexis._
import rsc.util._

object PrettyToken {
  def str(p: Printer, x: Token): Unit = {
    p.str(pretty(x)._1)
  }

  def repl(p: Printer, x: Token): Unit = {
    p.str(pretty(x)._2)
  }

  private def pretty(x: Token): (String, String) = {
    if (0 < x && x <= 100) {
      x match {
        case BOF => ("beginning of file", "BOF")
        case EOF => ("end of file", "EOF")
        case ERROR => ("error", "ERROR")
      }
    } else if (100 < x && x <= 200) {
      import rsc.lexis.scala._
      x match {
        case ABSTRACT => ("abstract", "ABSTRACT")
        case ARROW => ("=>", "ARROW")
        case AT => ("@", "AT")
        case BOF => ("beginning of file", "BOF")
        case CASE => ("case", "CASE")
        case CATCH => ("catch", "CATCH")
        case CLASS => ("class", "CLASS")
        case COLON => (":", "COLON")
        case COMMA => (",", "COMMA")
        case COMMENT => ("comment", "COMMENT")
        case DEF => ("def", "DEF")
        case DO => ("do", "DO")
        case DOT => (".", "DOT")
        case ELSE => ("else", "ELSE")
        case EOF => ("eof", "EOF")
        case EQUALS => ("equals", "EQUALS")
        case EXTENDS => ("extends", "EXTENDS")
        case FALSE => ("false", "FALSE")
        case FINAL => ("final", "FINAL")
        case FINALLY => ("finally", "FINALLY")
        case FOR => ("for", "FOR")
        case FORSOME => ("forSome", "FORSOME")
        case HASH => ("#", "HASH")
        case ID => ("identifier", "ID")
        case IF => ("if", "IF")
        case IMPLICIT => ("implicit", "IMPLICIT")
        case IMPORT => ("import", "IMPORT")
        case INTEND => ("interpolation end", "INTEND")
        case INTID => ("interpolation id", "INTID")
        case INTPART => ("interpolation part", "INTPART")
        case INTSPLICE => ("interpolation splice", "INTSPLICE")
        case INTSTART => ("interpolation start", "INTSTART")
        case LARROW => ("<-", "LARROW")
        case LAZY => ("lazy", "LAZY")
        case LBRACE => ("{", "LBRACE")
        case LBRACKET => ("[", "LBRACKET")
        case LITCHAR => ("character literal", "LITCHAR")
        case LITDOUBLE => ("double literal", "LITDOUBLE")
        case LITFLOAT => ("float literal", "LITFLOAT")
        case LITHEXINT => ("hexadecimal integer literal", "LITHEXINT")
        case LITHEXLONG => ("hexadecimal long literal", "LITHEXLONG")
        case LITINT => ("integer literal", "LITINT")
        case LITLONG => ("long literal", "LITLONG")
        case LITSTRING => ("string literal", "LITSTRING")
        case LITSYMBOL => ("symbol literal", "LITSYMBOL")
        case LPAREN => ("(", "LPAREN")
        case MATCH => ("match", "MATCH")
        case NEW => ("new", "NEW")
        case NEWLINE => ("newline", "NEWLINE")
        case NULL => ("null", "NULL")
        case OBJECT => ("object", "OBJECT")
        case OVERRIDE => ("override", "OVERRIDE")
        case PACKAGE => ("package", "PACKAGE")
        case PRIVATE => ("private", "PRIVATE")
        case PROTECTED => ("protected", "PROTECTED")
        case RBRACE => ("}", "RBRACE")
        case RBRACKET => ("]", "RBRACKET")
        case RETURN => ("return", "RETURN")
        case RPAREN => (")", "RPAREN")
        case SEALED => ("sealed", "SEALED")
        case SEMI => (";", "SEMI")
        case SUBTYPE => ("<:", "SUBTYPE")
        case SUPER => ("super", "SUPER")
        case SUPERTYPE => (">:", "SUPERTYPE")
        case THIS => ("this", "THIS")
        case THROW => ("throw", "THROW")
        case TRAIT => ("trait", "TRAIT")
        case TRUE => ("true", "TRUE")
        case TRY => ("try", "TRY")
        case TYPE => ("type", "TYPE")
        case USCORE => ("_", "USCORE")
        case VAL => ("val", "VAL")
        case VAR => ("var", "VAR")
        case VIEWBOUND => ("<%", "VIEWBOUND")
        case WHILE => ("while", "WHILE")
        case WHITESPACE => ("whitespace", "WHITESPACE")
        case WITH => ("with", "WITH")
        case XML => ("xml literal", "XML")
        case YIELD => ("yield", "YIELD")
        case CASECLASS => ("case class", "CASECLASS")
        case CASEOBJECT => ("case object", "CASEOBJECT")
        case NL1 => ("newline", "NL1")
        case NL2 => ("newline", "NL2")
      }
    } else if (200 < x && x <= 300) {
      ???
    } else {
      crash(s"unsupported token: $x")
    }
  }
}
