/* DigiDoc4J library
*
* This software is released under either the GNU Library General Public
* License (see LICENSE.LGPL).
*
* Note that the only valid version of the LGPL license as far as this
* project is concerned is the original GNU Library General Public License
* Version 2.1, February 1999
*/

package org.digidoc4j.impl.bdoc.asic;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

public class AsicContainerParserTest {

  @Test
  public void findingNextSignatureFileIndex_onEmptyContainer_shouldReturn_null() throws Exception {
    AsicParseResult result = parseContainer("testFiles/asics_without_signatures.bdoc");
    assertEquals(null, result.getCurrentUsedSignatureFileIndex());
  }

  @Test
  public void findingNextSignatureFileIndex_onContainerWithOneSignature_withoutIndex_shouldReturn_null() throws Exception {
    AsicParseResult result = parseContainer("testFiles/asics_for_testing.bdoc");
    assertEquals(null, result.getCurrentUsedSignatureFileIndex());
  }

  @Test
  public void findingNextSignatureFileIndex_onContainerWithOneSignature_withIndex0_shouldReturn_0() throws Exception {
    AsicParseResult result = parseContainer("testFiles/asics_with_one_signature.bdoc");
    assertEquals(Integer.valueOf(0), result.getCurrentUsedSignatureFileIndex());
  }

  @Test
  public void findingNextSignatureFileIndex_onContainerWithTwoSignature_shouldReturn_1() throws Exception {
    AsicParseResult result = parseContainer("testFiles/asics_testing_two_signatures.bdoc");
    assertEquals(Integer.valueOf(1), result.getCurrentUsedSignatureFileIndex());
  }

  @Test
  public void parseBdocContainer() throws Exception {
    AsicParseResult result = parseContainer("testFiles/two_signatures.bdoc");
    assertParseResultValid(result);
  }

  @Test
  public void parseBdocContainerStream() throws Exception {
    AsicContainerParser parser = new AsicContainerParser(new FileInputStream("testFiles/two_signatures.bdoc"));
    AsicParseResult result = parser.read();
    assertParseResultValid(result);
  }

  private AsicParseResult parseContainer(String path) {
    AsicContainerParser parser = new AsicContainerParser(path);
    AsicParseResult result = parser.read();
    return result;
  }

  private void assertParseResultValid(AsicParseResult result) {
    assertEquals("test.txt", result.getDataFiles().get(0).getName());
    assertEquals("META-INF/signatures0.xml", result.getSignatures().get(0).getName());
    assertEquals("META-INF/signatures1.xml", result.getSignatures().get(1).getName());
    assertEquals(Integer.valueOf(1), result.getCurrentUsedSignatureFileIndex());
    Assert.assertTrue(result.getManifestParser().containsManifestFile());
  }
}