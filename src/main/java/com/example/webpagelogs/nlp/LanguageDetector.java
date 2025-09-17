package com.example.webpagelogs.nlp;

import opennlp.tools.langdetect.*;
import opennlp.tools.util.model.ModelUtil;
import java.io.InputStream;

public class LanguageDetector {

    private static LanguageDetectorME detector;

    public static LanguageDetectorME getDetector() throws Exception {
        if (detector == null) {
            try (InputStream modelIn = LanguageDetector.class.getResourceAsStream("/lang-model.bin")) {
                LanguageDetectorModel model = new LanguageDetectorModel(modelIn);
                detector = new LanguageDetectorME(model);
            }
        }
        return detector;
    }
}
