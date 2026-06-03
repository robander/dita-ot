/*
 * This file is part of the DITA Open Toolkit project.
 *
 * Copyright 2026 Jarno Elovirta
 *
 * See the accompanying LICENSE file for applicable license.
 */
package org.dita.dost.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.Test;

public class RelaxNgValidationHelperTest {

  private static final File RESOURCE_DIR = new File("src/test/resources/postprocess-rng");

  @Test
  public void validateNormalizedTopicAgainstNormalizedShell() throws Exception {
    assertNotNull(System.getProperty("bundled_dita_dir"));
    RelaxNgValidationHelper.validate(
      new File(System.getProperty("bundled_dita_dir")),
      "urn:dita-ot:rng:basetopic-normalized.rng",
      new File(RESOURCE_DIR, "normalized-topic.dita")
    );
  }

  @Test
  public void validateTempTopicAgainstTempShell() throws Exception {
    assertNotNull(System.getProperty("bundled_dita_dir"));
    RelaxNgValidationHelper.validate(
      new File(System.getProperty("bundled_dita_dir")),
      "urn:dita-ot:rng:basetopic-temp.rng",
      new File(RESOURCE_DIR, "temp-topic.dita")
    );
  }

  @Test
  public void rejectTempAttributesInNormalizedShell() {
    assertNotNull(System.getProperty("bundled_dita_dir"));
    assertThrows(
      RelaxNgValidationHelper.ValidationFailure.class,
      () ->
        RelaxNgValidationHelper.validate(
          new File(System.getProperty("bundled_dita_dir")),
          "urn:dita-ot:rng:basetopic-normalized.rng",
          new File(RESOURCE_DIR, "temp-topic.dita")
        )
    );
  }
}
