/*
 * Copyright 2015 Joseph Moore
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

import com.google.auto.common.MoreTypes;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by adm.jmooreoa on 1/5/15.
 */
@ParametersAreNonnullByDefault
public final class TypesExt {
    private TypesExt() {}

    public static TypeMirror notPrimitive(Types types, TypeMirror type) {
        if (type instanceof PrimitiveType) {
            return types.boxedClass((PrimitiveType) type).asType();
        }
        return type;
    }

    private static final Set<TypeKind> PRIMITIVES = EnumSet.of(
            TypeKind.BOOLEAN, TypeKind.BYTE, TypeKind.CHAR, TypeKind.DOUBLE, TypeKind.FLOAT, TypeKind.INT, TypeKind.LONG, TypeKind.SHORT
    );

    public static TypeMirror maybePrimitive(Types types, TypeMirror type) {
        if (PRIMITIVES.contains(type.getKind())) {
            return types.getPrimitiveType(type.getKind());
        }
        return type;
    }

}
