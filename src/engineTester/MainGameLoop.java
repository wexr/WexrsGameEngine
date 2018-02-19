package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //************ TERRAIN TEXTURES ****************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));

        //***********************************************

        RawModel treeModel = OBJLoader.loadObjModel("tree", loader);

        TexturedModel tree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));

        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLightning(true);

        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);


        TexturedModel tree2 = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));

        TexturedModel sign = new TexturedModel(OBJLoader.loadObjModel("sign", loader), new ModelTexture(loader.loadTexture("andras1")));
        grass.getTexture().setUseFakeLightning(true);


        //grass & fern
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i = 0; i < 400; i++){

            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.6f));
        }

        //low poly trees
        List<Entity> entities2 = new ArrayList<Entity>();
        Random random2 = new Random();
        for(int i = 0; i < 200; i++){
            entities2.add(new Entity(tree2, new Vector3f(random2.nextFloat() * 800 - 400, 0, random2.nextFloat() * -600), 0, 0, 0, 0.3f));
            entities2.add(new Entity(tree, new Vector3f(random2.nextFloat() * 800 - 400, 0, random2.nextFloat() * -600), 0, 0, 0, 3));    // old tree model

        }

        // big sign
        List<Entity> entities3 = new ArrayList<Entity>();
        for(int i = 0; i < 1; i++){
            entities3.add(new Entity(sign, new Vector3f(-100, 0, -300), 0, -90, 0, 7));


        }





        Light light = new Light(new Vector3f(20000,40000,2000),new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);


        MasterRenderer renderer = new MasterRenderer();

        //********** PLAYER OBJECT *************

        RawModel bunnyModel = OBJLoader.loadObjModel("stanfordBunny", loader);
        RawModel girlModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel stanfornBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
        TexturedModel playerModel = new TexturedModel(girlModel, new ModelTexture(loader.loadTexture("playerTexture")));

        Player player = new Player(stanfornBunny, new Vector3f(100, 0, -50), 0, 0, 0, 0.6f);
        Camera camera = new Camera(player);

         //******************************************

        while(!Display.isCloseRequested()){
            camera.move();
            player.move();
            renderer.processEntity(player);

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);


            for(Entity entity:entities){
                renderer.processEntity(entity);
            }

            for(Entity entity:entities2){
                renderer.processEntity(entity);
            }

            for(Entity entity:entities3){
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
