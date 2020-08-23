package dev.penguinz.Sylk.input;

import dev.penguinz.Sylk.Application;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InputManager {

    private final HashMap<Key, List<Modifier>> keyPresses = new HashMap<>();
    private final HashMap<Key, List<Modifier>> keyHolds = new HashMap<>();
    private final HashMap<Key, List<Modifier>> keyHoldsDelayed = new HashMap<>();
    private final HashMap<Key, List<Modifier>> keyReleases = new HashMap<>();

    private final Vector2f mousePosition = new Vector2f(0, 0);
    private final List<Integer> mousePresses = new ArrayList<>();
    private final List<Integer> mouseHolds = new ArrayList<>();
    private final List<Integer> mouseReleases = new ArrayList<>();

    GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int keyCode, int scancode, int action, int mods) {
            Key key = Key.getKeyCode(keyCode);
            if(key == null)
                return;
            List<Modifier> modifiers = new ArrayList<>();
            for (Modifier value : Modifier.values()) {
                if((mods & value.modCode) == value.modCode)
                    modifiers.add(value);
            }
            if(action == GLFW.GLFW_PRESS) {
                keyPresses.put(key, modifiers);
                keyHolds.put(key, modifiers);
            } else if(action == GLFW.GLFW_REPEAT)
                keyHoldsDelayed.put(key, modifiers);
            else if(action == GLFW.GLFW_RELEASE) {
                keyReleases.put(key, modifiers);
                keyHolds.remove(key);
            }
        }
    };

    GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if(action == GLFW.GLFW_PRESS) {
                mousePresses.add(button);
                mouseHolds.add(button);
            } else if(action == GLFW.GLFW_REPEAT)
                mouseHolds.add(button);
            else if(action == GLFW.GLFW_RELEASE)
                mouseReleases.add(button);
        }
    };

    GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            mousePosition.x = (float) xpos;
            mousePosition.y = (float) ypos * -1 + Application.getInstance().getWindowHeight();
        }
    };

    public InputManager(long windowHandle) {
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_LOCK_KEY_MODS, GLFW.GLFW_TRUE);
        setCallbacks(windowHandle);
    }

    private void setCallbacks(long windowHandle) {
        GLFW.glfwSetKeyCallback(windowHandle, keyCallback);
        GLFW.glfwSetCursorPosCallback(windowHandle, cursorPosCallback);
        GLFW.glfwSetMouseButtonCallback(windowHandle, mouseButtonCallback);
    }

    public void clearInputs() {
        mousePresses.clear();
        mouseReleases.clear();
        keyPresses.clear();
        keyHoldsDelayed.clear();
        keyReleases.clear();
    }

    public boolean isKeyPressed(Key key, Modifier... modifiers) {
        if(keyPresses.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyPresses.get(key).contains(modifier))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean isKeyDown(Key key, Modifier... modifiers) {
        if(keyHolds.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyHolds.get(key).contains(modifier))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean isKeyHeld(Key key, Modifier... modifiers) {
        if(keyHoldsDelayed.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyHoldsDelayed.get(key).contains(modifier))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean isKeyReleased(Key key, Modifier... modifiers) {
        if(keyReleases.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyPresses.get(key).contains(modifier))
                    return false;
            }
            return true;
        }
        return false;
    }

    public float getHorizontalInput() {
        float value = 0;
        if(isKeyDown(Key.KEY_D) || isKeyDown(Key.KEY_RIGHT))
            value += 1;
        if(isKeyDown(Key.KEY_A) || isKeyDown(Key.KEY_LEFT))
            value -= 1;
        return value;
    }

    public float getVerticalInput() {
        float value = 0;
        if(isKeyDown(Key.KEY_W) || isKeyDown(Key.KEY_UP))
            value += 1;
        if(isKeyDown(Key.KEY_S) || isKeyDown(Key.KEY_DOWN))
            value -= 1;
        return value;
    }

    public boolean isMouseButtonPressed(int button) {
        return mousePresses.contains(button);
    }

    public boolean isMouseButtonDown(int button) {
        return mouseHolds.contains(button);
    }

    public boolean isMouseButtonReleased(int button) {
        return mouseReleases.contains(button);
    }

    public int getMousePosX() {
        return (int) mousePosition.x;
    }

    public int getMousePosY() {
        return (int) mousePosition.y;
    }

    public enum Modifier {

        SHIFT(GLFW.GLFW_MOD_SHIFT),
        CONTROL(GLFW.GLFW_MOD_CONTROL),
        ALT(GLFW.GLFW_MOD_ALT),
        CAPS_LOCK(GLFW.GLFW_MOD_CAPS_LOCK),
        NUM_LOCK(GLFW.GLFW_MOD_NUM_LOCK);

        private final int modCode;

        Modifier(int modCode) {
            this.modCode = modCode;
        }

    }

}
