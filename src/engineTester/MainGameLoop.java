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
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();

        // OpenGL expects vertices to be defined counter clockwise by default
        //All of our vertecis will be in 3 points, and no matter what point I chose to start from, ill just have to rotate counter clock wise to make the triangle.






        RawModel model = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }

        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while(!Display.isCloseRequested()){
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);


            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }


        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
