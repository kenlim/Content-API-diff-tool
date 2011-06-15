/* Copyright (c) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.xmldiff

import scala.xml._

/**
 * Main class for xmldiff like tool. It has the following features:
 *  - check that 'expected' document is included in 'actual'. All elements and attributes
 *    have to be present in the 'actual' document, maybe in a different order.
 *  - '-notext' ignores all text nodes. Only document structure matters, contents of elements
 *    and attributes is skipped.
 *  - '-i' allows to specify a list of XPath-like paths to be ignored during the diff. It
 *    understands only '/' and '//', and element names have no namespace (they match any
 *    namespace). For instance, -i //updated feed/author will ignore any updated element,
 *    and /feed/author elements (all paths are absolute).
 * 
 * @author Iulian Dragos
 */
object Main {
  /** First document (expected). */
  private var xml1: Elem = _ 
  
  /** Second document (expected). */
  private var xml2: Elem = _ 
  
  private var notext = false

  private var ignorePaths: List[SimplePath] = Nil
  
  def main(args: Array[String]) {
    parseArgs(args.toList)
    
    val comparison = new Comparison
    comparison.notext = notext
    comparison.ignorePaths = ignorePaths
    comparison(xml1, xml2) match {
      case NoDiff => println("Documents are similar.")
      case diff   => println(diff)
    }
  }
  
  private def parseArgs(args: List[String]) {
    if (args.isEmpty) printUsage
    
    var as = args
    while (as != Nil) as match {
      case "-notext" :: rest =>
        notext = true
        as = rest

      case "-i" :: rest =>
        ignorePaths = for (p <- rest) yield new SimplePath(p)
        as = Nil
        
      case file1 :: file2 :: rest =>
        xml1 = XML.loadFile(file1)
        xml2 = XML.loadFile(file2)
        as = rest

      case _ =>
        printUsage
    }
  }
  
  private def printUsage {
    println("""
xmldiff expected.xml actual.xml [-notext] [-i ignores]

    -notext Ignore text nodes. Only document structure matters (elements and attributes).
    -i      Provide a list of XPath elements to be ignored. Understands only '/' and '//',
            and has no namespace support.

    Compares the two documents for similarity. Element order is not signifficant,
    all other nodes are ordered. This is NOT a two-way diff tool. It checks that <expected.xml>
    is included in <actual.xml>. For a stricter notion of similarity, run the tool twice and
    switch the order of the arguments.
""")
   System.exit(0)
  }
}
