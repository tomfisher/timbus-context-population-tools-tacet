/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
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
