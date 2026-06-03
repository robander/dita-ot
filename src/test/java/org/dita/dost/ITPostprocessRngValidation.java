/*
 * This file is part of the DITA Open Toolkit project.
 *
 * Copyright 2026 Jarno Elovirta
 *
 * See the accompanying LICENSE file for applicable license.
 */
package org.dita.dost;

import static org.dita.dost.AbstractIntegrationTest.Transtype.DITA;
import static org.dita.dost.AbstractIntegrationTest.Transtype.PREPROCESS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import org.dita.dost.util.RelaxNgValidationHelper;
import org.junit.jupiter.api.Test;

public class ITPostprocessRngValidation extends AbstractIntegrationTest {

  public ITPostprocessRngValidation builder() {
    return new ITPostprocessRngValidation();
  }

  @Override
  Transtype getTranstype(Transtype transtype) {
    return transtype;
  }

  @Test
  public void validateTempOutputAgainstTempShell() throws Throwable {
    final File actDir = builder()
      .name("postprocess-rng-build")
      .transtype(PREPROCESS)
      .input(Paths.get("main.ditamap"))
      .ditaDir(bundledDitaDir())
      .put("generate-debug-attributes", "true")
      .run();

    final File topic = new File(actDir, "topic.dita");
    assertTrue(topic.isFile(), "Expected preprocess topic output");
    RelaxNgValidationHelper.validate(bundledDitaDir(), "urn:dita-ot:rng:basetopic-temp.rng", topic);
    assertThrows(
      RelaxNgValidationHelper.ValidationFailure.class,
      () -> RelaxNgValidationHelper.validate(bundledDitaDir(), "urn:dita-ot:rng:basetopic-normalized.rng", topic)
    );
  }

  @Test
  public void validateNormalizedOutputAgainstNormalizedShell() throws Throwable {
    final File actDir = builder()
      .name("postprocess-rng-build")
      .transtype(DITA)
      .input(Paths.get("main.ditamap"))
      .ditaDir(bundledDitaDir())
      .run();

    final File topic = new File(actDir, "topic.dita");
    assertTrue(topic.isFile(), "Expected normalized topic output");
    RelaxNgValidationHelper.validate(bundledDitaDir(), "urn:dita-ot:rng:basetopic-normalized.rng", topic);
  }

  private File bundledDitaDir() {
    return new File(System.getProperty("bundled_dita_dir"));
  }
}
