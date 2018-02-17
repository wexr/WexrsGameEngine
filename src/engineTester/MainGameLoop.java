package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();

        // OpenGL expects vertices to be defined counter clockwise by default
        //All of our vertecis will be in 3 points, and no matter what point I chose to start from, ill just have to rotate counter clock wise to make the triangle.






        RawModel model = OBJLoader.loadObjModel("stall", loader);
        //ModelTexture texture = new ModelTexture(loader.loadTexture("andras1"));
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        //First object
        Entity entity = new Entity(staticModel,  new Vector3f(0,-4,-25),0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-22), new Vector3f(1,1,1));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {
            entity.increaseRotation(0,0.3f,0); // Rotate the new cube along x and y.
            camera.move();
            renderer.processEntity(entity); //object entity nr 1
            renderer.render(light, camera);
            DisplayManager.updateDisplay();

        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}
