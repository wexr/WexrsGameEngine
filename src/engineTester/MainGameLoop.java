package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
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






        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        //ModelTexture texture = new ModelTexture(loader.loadTexture("andras1"));
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("green")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        //First object
        Entity entity = new Entity(staticModel,  new Vector3f(0,-4,-25),0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-22), new Vector3f(1,1,1));

        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
            //game logic
            // entity object will move towards the right side while rotating on the Y axe.
            //entity.increasePosition(0, 0, -0.01f); //Move the object form left to right.
            //entity.increaseRotation(0, 1, 0);   // Rotate the picture on the y axe.
            entity.increaseRotation(0,0.3f,0); // Rotate the new cube along x and y.
            camera.move();
            renderer.prepare();
            shader.start();
            //render
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();

        }

        shader.cleanUP();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}
