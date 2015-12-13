package com.ld34;

import com.ld34.game.Game;
import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.util.GameState;
import com.ld34.util.Keyboard;
import com.ld34.util.Mouse;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created on 12/11/2015.
 */
public class Main implements Runnable {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final double TARGET_FPS = 30;
    private static final boolean DEBUG = false;

    private boolean running = false;

    private static GameState currentGameState;
    private long window;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;

    public static void main(String[] args) {
        new Thread(new Main(), "LudumDare34-tyty5949").start();
    }

    public void run() {
        try {
            init();
            loop();

            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            glfwTerminate();
            errorCallback.release();
        }
    }

    private void init() {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Ludum Dare 34", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new Keyboard());
        glfwSetCursorPosCallback(window, cursorPosCallback = new Mouse.CursorPos());
        glfwSetMouseButtonCallback(window, mouseButtonCallback = new Mouse.MouseButton());
        glfwSetScrollCallback(window, scrollCallback = new Mouse.Scroll());

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void loop() {
        // Initialize OpenGL
        GL.createCapabilities();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(.1f, .1f, .1f, 1.0f);

        Textures.init();
        Fonts.init();
        currentGameState = new Game();

        long now = System.nanoTime();
        long lastTime = now;
        double deltaR = 0.0;
        double deltaU = 0.0;
        double fns = 1000000000.0 / TARGET_FPS;
        long lastRender = now;
        long lastUpdate = now;
        double renderDelta = 0.0;
        double updateDelta = 0.0;

        running = true;

        while (running) {
            if (glfwWindowShouldClose(window) == GL_TRUE)
                running = false;

            now = System.nanoTime();
            deltaR += (now - lastTime) / fns;
            deltaU += (now - lastTime) / fns;
            lastTime = now;

            if (deltaR >= 1.0) {
                render(renderDelta);
                renderDelta = (now - lastRender) / 1000000000.0f;
                lastRender = now;
                deltaR--;
            }

            if (deltaU >= 1.0) {
                update(updateDelta);
                updateDelta = (now - lastUpdate) / 1000000000.0f;
                lastUpdate = now;
                deltaU--;
            }
        }

    }

    private double fps;
    private int fpsCount = 0;
    private DecimalFormat decimalFormat = new DecimalFormat("#.000");

    private void render(double delta) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();

        if (currentGameState != null) {
            if (currentGameState.initialized)
                currentGameState.render(delta, this);
            else
                currentGameState.init(this);
        }

        if (DEBUG) {
            if (fpsCount > 20) {
                fpsCount = 0;
                fps = 1.0 / delta;
            }
            fpsCount++;
            Fonts.debug.drawText("FPS: " + decimalFormat.format(fps), 0, 576, Colors.DEBUG_FONT_COLOR);
            Fonts.debug.drawText("GameState: " + currentGameState.name, 0, 600, Colors.DEBUG_FONT_COLOR);
        }

        glfwSwapBuffers(window);
    }

    private void update(double delta) {
        if (currentGameState != null) {
            if (currentGameState.initialized)
                currentGameState.update(delta, this);
        }
    }

    public static void setGameState(GameState newGameState) {
        currentGameState = newGameState;
    }
}
