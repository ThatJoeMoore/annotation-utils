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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeAndWriteProcessorTest {

    @Mock
    private AnalyzeAndWriteProcessor.Analyzer<Integer> analyzer;
    @Mock
    private AnalyzeAndWriteProcessor.Writer<Integer> writer;
    @Mock
    private ProcessingEnvironment processingEnv;
    @Mock
    private RoundEnvironment roundEnv;
    @Mock
    private Messager messager;

    private Fixture fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new Fixture(analyzer, writer);
        when(processingEnv.getMessager())
                .thenReturn(messager);
        fixture.init(processingEnv);
    }

    @Test
    public void testProcess() throws Exception {
        Set<? extends TypeElement> set = new HashSet<TypeElement>();

        final int blueprint = 12;

        //noinspection unchecked
        when(analyzer.analyze(any(Set.class)))
                .thenReturn(blueprint);
        when(writer.write(anyInt())).thenReturn(true);

        boolean result = fixture.process(set, roundEnv);
        assertTrue(result);

        verify(analyzer).init(same(processingEnv), same(roundEnv), any(Logger.class));
        verify(writer).init(same(processingEnv), same(roundEnv), any(Logger.class));

        verify(analyzer).analyze(same(set));
        verify(writer).write(blueprint);

        verifyNoMoreInteractions(analyzer, writer);
    }

    private static final class Fixture extends AnalyzeAndWriteProcessor<Integer> {
        private final Analyzer<Integer> analyzer;
        private final Writer<Integer> writer;

        public Fixture(Analyzer<Integer> analyzer, Writer<Integer> writer) {
            this.analyzer = analyzer;
            this.writer = writer;
        }

        @Override
        protected Analyzer<Integer> getAnalyzer() {
            return analyzer;
        }

        @Override
        protected Writer<Integer> getWriter() {
            return writer;
        }
    }
}