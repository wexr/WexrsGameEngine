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

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));

        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLightning(true);

        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);


        TexturedModel tree2 = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i = 0; i < 4000; i++){
            //entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));    // old tree model
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.6f));
        }

        List<Entity> entities2 = new ArrayList<Entity>();
        Random random2 = new Random();
        for(int i = 0; i < 2000; i++){
            entities2.add(new Entity(tree2, new Vector3f(random2.nextFloat() * 800 - 400, 0, random2.nextFloat() * -600), 0, 0, 0, 0.3f));

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

            for(Entity entity:entities2){
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
