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

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by adm.jmooreoa on 12/31/14.
 */
public abstract class AbstractAnalyzer<Blueprint> implements AnalyzeAndWriteProcessor.Analyzer<Blueprint> {

    private ProcessingEnvironment processingEnv;
    private RoundEnvironment roundEnv;
    private Logger logger;

    @Override
    public void init(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv, Logger logger) {
        this.processingEnv = processingEnv;
        this.roundEnv = roundEnv;
        this.logger = logger;
    }

    protected ProcessingEnvironment processingEnv() {
        return processingEnv;
    }

    protected RoundEnvironment roundEnv() {
        return roundEnv;
    }

    protected Logger logger() {
        return logger;
    }

    protected final Types types() {
        return processingEnv.getTypeUtils();
    }

    protected final Elements elements() {
        return processingEnv.getElementUtils();
    }

    protected final Messager messager() {
        return processingEnv.getMessager();
    }

    protected final Filer filer() {
        return processingEnv.getFiler();
    }

}
