/*
 * Copyright 2014 Joseph Moore
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thatjoemoore.utils.annotations;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by adm.jmooreoa on 12/30/14.
 */
public abstract class AnalyzeAndWriteProcessor<Blueprint> extends AbstractProcessorExt {

    @Override
    protected boolean processAbortable(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws AbortProcessingException {
        Analyzer<Blueprint> analyzer = getAnalyzer();
        Writer<Blueprint> writer = getWriter();

        analyzer.init(processingEnv, roundEnv, logger());
        writer.init(processingEnv, roundEnv, logger());

        return writer.write(analyzer.analyze(annotations));
    }

    protected abstract Analyzer<Blueprint> getAnalyzer();
    protected abstract Writer<Blueprint> getWriter();

    public static interface Analyzer<Blueprint> {
        void init(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv, Logger logger);

        Blueprint analyze(Set<? extends TypeElement> annotations);
    }

    public static interface Writer<Blueprint> {
        void init(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv, Logger logger);

        boolean write(Blueprint blueprint);
    }

}
