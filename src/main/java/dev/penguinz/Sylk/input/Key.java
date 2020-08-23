package dev.penguinz.Sylk.input;

import org.lwjgl.glfw.GLFW;

public enum Key {

    KEY_UNKNOWN(GLFW.GLFW_KEY_UNKNOWN, "", "", false),
    KEY_SPACE(GLFW.GLFW_KEY_SPACE, " ", " ", true),
    KEY_APOSTROPHE(GLFW.GLFW_KEY_APOSTROPHE, "'", "\"", true),
    KEY_COMMA(GLFW.GLFW_KEY_COMMA, ",", "<", true),
    KEY_MINUS(GLFW.GLFW_KEY_MINUS, "-", "_", true),
    KEY_PERIOD(GLFW.GLFW_KEY_PERIOD, ".", ">", true),
    KEY_SLASH(GLFW.GLFW_KEY_SLASH, "/", "?", true),
    KEY_0(GLFW.GLFW_KEY_0, "0", ")", true),
    KEY_1(GLFW.GLFW_KEY_1, "1", "!", true),
    KEY_2(GLFW.GLFW_KEY_2, "2", "@", true),
    KEY_3(GLFW.GLFW_KEY_3, "3", "#", true),
    KEY_4(GLFW.GLFW_KEY_4, "4", "$", true),
    KEY_5(GLFW.GLFW_KEY_5, "5", "%", true),
    KEY_6(GLFW.GLFW_KEY_6, "6", "^", true),
    KEY_7(GLFW.GLFW_KEY_7, "7", "&", true),
    KEY_8(GLFW.GLFW_KEY_8, "8", "*", true),
    KEY_9(GLFW.GLFW_KEY_9, "9", "(", true),
    KEY_SEMICOLON(GLFW.GLFW_KEY_SEMICOLON, ";", ":", true),
    KEY_EQUAL(GLFW.GLFW_KEY_EQUAL, "=", "+", true),
    KEY_A(GLFW.GLFW_KEY_A, "a", "A", true),
    KEY_B(GLFW.GLFW_KEY_B, "b", "B", true),
    KEY_C(GLFW.GLFW_KEY_C, "c", "C", true),
    KEY_D(GLFW.GLFW_KEY_D, "d", "D", true),
    KEY_E(GLFW.GLFW_KEY_E, "e", "E", true),
    KEY_F(GLFW.GLFW_KEY_F, "f", "F", true),
    KEY_G(GLFW.GLFW_KEY_G, "g", "G", true),
    KEY_H(GLFW.GLFW_KEY_H, "h", "H", true),
    KEY_I(GLFW.GLFW_KEY_I, "i", "I", true),
    KEY_J(GLFW.GLFW_KEY_J, "j", "J", true),
    KEY_K(GLFW.GLFW_KEY_K, "k", "K", true),
    KEY_L(GLFW.GLFW_KEY_L, "l", "L", true),
    KEY_M(GLFW.GLFW_KEY_M, "m", "M", true),
    KEY_N(GLFW.GLFW_KEY_N, "n", "N", true),
    KEY_O(GLFW.GLFW_KEY_O, "o", "O", true),
    KEY_P(GLFW.GLFW_KEY_P, "p", "P", true),
    KEY_Q(GLFW.GLFW_KEY_Q, "q", "Q", true),
    KEY_R(GLFW.GLFW_KEY_R, "r", "R", true),
    KEY_S(GLFW.GLFW_KEY_S, "s", "S", true),
    KEY_T(GLFW.GLFW_KEY_T, "t", "T", true),
    KEY_U(GLFW.GLFW_KEY_U, "u", "U", true),
    KEY_V(GLFW.GLFW_KEY_V, "v", "V", true),
    KEY_W(GLFW.GLFW_KEY_W, "w", "W", true),
    KEY_X(GLFW.GLFW_KEY_X, "x", "X", true),
    KEY_Y(GLFW.GLFW_KEY_Y, "y", "Y", true),
    KEY_Z(GLFW.GLFW_KEY_Z, "z", "Y", true),
    KEY_LEFT_BRACKET(GLFW.GLFW_KEY_LEFT_BRACKET, "[", "{", true),
    KEY_BACKSLASH(GLFW.GLFW_KEY_BACKSLASH, "\\", "|", true),
    KEY_RIGHT_BRACKET(GLFW.GLFW_KEY_RIGHT_BRACKET, "]", "}", true),
    KEY_BACKTICK(GLFW.GLFW_KEY_GRAVE_ACCENT, "`", "~", true),
    KEY_ESCAPE(GLFW.GLFW_KEY_ESCAPE, "esc", "", false),
    KEY_ENTER(GLFW.GLFW_KEY_ENTER, "\n", "\n", true),
    KEY_TAB(GLFW.GLFW_KEY_TAB, "\t", "\t", true),
    KEY_BACKSPACE(GLFW.GLFW_KEY_BACKSPACE, "backspace", "backspace", false),
    KEY_INSERT(GLFW.GLFW_KEY_INSERT, "ins", "ins", false),
    KEY_DELETE(GLFW.GLFW_KEY_DELETE, "del", "del", false),
    KEY_RIGHT(GLFW.GLFW_KEY_RIGHT, "right arrow", "right arrow", false),
    KEY_LEFT(GLFW.GLFW_KEY_LEFT, "left arrow", "left arrow", false),
    KEY_DOWN(GLFW.GLFW_KEY_DOWN, "down arrow", "down arrow", false),
    KEY_UP(GLFW.GLFW_KEY_UP, "up arrow", "up arrow", false),
    KEY_PAGE_UP(GLFW.GLFW_KEY_PAGE_UP, "page up", "page up", false),
    KEY_PAGE_DOWN(GLFW.GLFW_KEY_PAGE_DOWN, "page down", "page down", false),
    KEY_HOME(GLFW.GLFW_KEY_HOME, "home", "home", false),
    KEY_END(GLFW.GLFW_KEY_END, "end", "end", false),
    KEY_CAPS_LOCK(GLFW.GLFW_KEY_CAPS_LOCK, "caps lock", "caps lock", false),
    KEY_SCROLL_LOCK(GLFW.GLFW_KEY_SCROLL_LOCK, "scroll lock", "scroll lock", false),
    KEY_NUM_LOCK(GLFW.GLFW_KEY_NUM_LOCK, "num lock", "num lock", false),
    KEY_PRINT_SCREEN(GLFW.GLFW_KEY_PRINT_SCREEN, "prtsc", "prtsc", false),
    KEY_F1(GLFW.GLFW_KEY_F1, "f1", "f1", false),
    KEY_F2(GLFW.GLFW_KEY_F2, "f2", "f2", false),
    KEY_F3(GLFW.GLFW_KEY_F3, "f3", "f3", false),
    KEY_F4(GLFW.GLFW_KEY_F4, "f4", "f4", false),
    KEY_F5(GLFW.GLFW_KEY_F5, "f5", "f5", false),
    KEY_F6(GLFW.GLFW_KEY_F6, "f6", "f6", false),
    KEY_F7(GLFW.GLFW_KEY_F7, "f7", "f7", false),
    KEY_F8(GLFW.GLFW_KEY_F8, "f8", "f8", false),
    KEY_F9(GLFW.GLFW_KEY_F9, "f9", "f9", false),
    KEY_F10(GLFW.GLFW_KEY_F10, "f10", "f10", false),
    KEY_F11(GLFW.GLFW_KEY_F11, "f11", "f11", false),
    KEY_F12(GLFW.GLFW_KEY_F12, "f12",  "f12",false),
    KEY_LEFT_SHIFT(GLFW.GLFW_KEY_LEFT_SHIFT, "left shift", "left shift", false),
    KEY_LEFT_CONTROL(GLFW.GLFW_KEY_LEFT_CONTROL, "left control", "left control", false),
    KEY_LEFT_ALT(GLFW.GLFW_KEY_LEFT_ALT, "left alt",  "left alt", false),
    KEY_RIGHT_SHIFT(GLFW.GLFW_KEY_RIGHT_SHIFT, "right shift",  "right shift", false),
    KEY_RIGHT_CONTROL(GLFW.GLFW_KEY_RIGHT_CONTROL, "right control", "right control", false),
    KEY_RIGHT_ALT(GLFW.GLFW_KEY_RIGHT_ALT, "right alt", "right alt", false);

    private final int keyCode;
    private final String character;
    private final String altCharacter;
    private final boolean displayable;

    Key(int keyCode, String character, String altCharacter, boolean displayable) {
        this.keyCode = keyCode;
        this.character = character;
        this.altCharacter = altCharacter;
        this.displayable = displayable;
    }

    public static Key getKeyCode(int keyCode) {
        for(Key key: Key.values()) {
            if(keyCode == key.keyCode)
                return key;
        }
        return null;
    }

    @Override
    public String toString() {
        return character;
    }

    public String getCharacter() {
        return character;
    }

    public String getAltCharacter() {
        return altCharacter;
    }

    public boolean isDisplayable() {
        return displayable;
    }
}
