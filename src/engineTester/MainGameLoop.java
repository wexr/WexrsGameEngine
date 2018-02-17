package engineTester;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

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
        /*
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

        float[] textureCoords = {
                0,0,            //V0
                0,1,            //V1
                1,1,            //V2
                1,0             //V3
        };
        */

        // New cube vertices data

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("andras1"));
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("feelsbadman")));

        //First object moved -1 verticle.
        Entity entity = new Entity(staticModel, new Vector3f(0,0,-5),0,0,0,1);
        Entity entity2 = new Entity(staticModel, new Vector3f(-2,0,-5),0,0,0,0.5f);
        Entity entity3 = new Entity(staticModel, new Vector3f(+2,0,-5),0,0,0,0.5f);

        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
            //game logic
                                            // entity object will move towards the right side while rotating on the Y axe.
            //entity.increasePosition(0, 0, -0.01f); //Move the object form left to right.
                                            //entity.increaseRotation(0, 1, 0);   // Rotate the picture on the y axe.
            entity.increaseRotation(1,1,0); // Rotate the new cube along x and y.
            entity2.increaseRotation(1,1,0); // Rotate the new cube along x and y.
            entity3.increaseRotation(1,1,0); // Rotate the new cube along x and y.
            camera.move();
            renderer.prepare();
            shader.start();
            //render
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            renderer.render(entity2, shader);
            renderer.render(entity3, shader);
            shader.stop();
            DisplayManager.updateDisplay();

        }

        shader.cleanUP();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}
