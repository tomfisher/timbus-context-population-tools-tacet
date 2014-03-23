/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
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

package squirrel.controller;

public class ValidationResult {
    public enum Type { VALID, ERROR, WARNING; }

    public Type type;
    public boolean result;
    public String reason;

    public ValidationResult(Type type, boolean result, String reason) {
        this.type = type;
        this.result = result;
        this.reason = reason;
    }

    public static ValidationResult createValidResult() {
        return createValidResult("");
    }

    public static ValidationResult createValidResult(String reason) {
        return new ValidationResult(Type.VALID, true, reason);
    }

    public static ValidationResult createErrorResult(String reason) {
        return new ValidationResult(Type.ERROR, false, reason);
    }

    public static ValidationResult createDummyErrorResult() {
        return createErrorResult("Something went wrong.");
    }

    public static ValidationResult createWarningResult(String reason) {
        return new ValidationResult(Type.WARNING, true, reason);
    }

    @Override
    public String toString() {
        return type + ": " + reason;
    }
}
