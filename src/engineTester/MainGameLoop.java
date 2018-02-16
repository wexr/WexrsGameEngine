package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        // OpenGL expects vertices to be defined counter clockwise by default
        //All of our vertecis will be in 3 points, and no matter what point I chose to start from, ill just have to rotate counter clock wise to make the triangle.


        // Changed to use indices aswell instead of only vertices.
        /*float[] vertices = {
                //First Triangle from diagram
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                //Second Triangle from diagram
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };*/

        float[] vertices = {
                -0.5f,0.5f,0,   //V0
                -0.5f,-0.5f,0,  //V1
                0.5f,-0.5f,0,   //V2
                0.5f,0.5f,0     //V3
        };

        int[] indices = {
                0,1,3,          // Top left triangle ( V0, V1, V3)
                3,1,2           // Bottom right triangle ( V3, V1, V2)

        };

        RawModel model = loader.loadToVAO(vertices, indices);


        while(!Display.isCloseRequested()) {
            renderer.prepare();

            //game logic
            //render
            renderer.render(model);
            DisplayManager.updateDisplay();

        }

        loader.cleanUp();

        DisplayManager.closeDisplay();

    }
}
