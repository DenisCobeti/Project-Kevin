package engine2D;

import gfx.MonoFont;

/**
 * Clase que contiene el hilo del motor sobre el que se ejecuta el juego.
 * Cuenta con todos los parametros de las clases del paquete.
 * 
 * @author Project Kevin
 */
public class GameContainer implements Runnable {
    
    private final double UPDATE_CAP = 1.0/60.0; // deltaTime min. 1/25 max. 1/60
    private Config config;
    
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;
    
    private final Object pauseLock = new Object();
    private boolean running = false;
    private boolean paused = false;

    /**
     * Constructor de la clase
     * @param game Juego que hace uso del motor gráfico
     */
    public GameContainer(AbstractGame game) {
        this.game = game;
        config = Config.getInstance();
        window = new Window(this);
    }
    
    /**
     * Crea las instancias de los objetos del motor y lanza su thread
     */
    public void start() {
        window.loadCanvas();
        renderer = new Renderer(this);
        renderer.setBackground(game.getBackground());
        input = new Input(this);
        
        thread = new Thread(this);
        thread.run();
    }
    
    /**
     * Se para totalmete el motor
     */
    public void stop() {
        running = false;
        if (paused) resume();
    }
    
    @Override
    public void run() {
        running = true;
        
        // Variables básicas
        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1e9d;
        double passedTime = 0;
        double unprocessedTime = 0;
        
        // Variables de monitorización
        double frameTime = 0;
        int frames = 0;
        int updates = 0;
        int fps = 0;
        int ups = 0;
   
        // Bucle principal del motor gráfico
        while(running) {
            render = false;
            
            firstTime = System.nanoTime() / 1e9d;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            
            unprocessedTime += passedTime;
            frameTime += passedTime;
            
            // Se llevan a cabo tantas actualizaciones como sean necesarias
            while(unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;
                
                game.update(this, (float)UPDATE_CAP);
                
                input.update();
                
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    ups = updates;
                    frames = 0;
                    updates = 0;
                }
                updates++;
            }
            
            // Se llama al renderizador para que dibuje en pantalla
            if(render) {
                renderer.clear();
                game.render(this, renderer);
                renderer.drawText("ups:" + ups, MonoFont.STANDARD,
                                                config.getScreenWidth()  - 115, 
                                                config.getScreenHeight() - 42,
                                                0xffffffff);
                renderer.drawText("fps:" + fps, MonoFont.STANDARD,
                                                config.getScreenWidth()  - 115, 
                                                config.getScreenHeight() - 20,
                                                0xffff9900);
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1); // Se evita saturar al TaskManager
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            
            // Se pausa el motor de ser necesario
            if(paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
        dispose();
    }
    
    /**
     * Se pausa el motor
     */
    public void pause() {
        //if (!running) throw new NotRunningException();
        paused = true;
    }
    
    /**
     * Si el motor estaba pausado se reanuda si ejecución
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    /**
     * Deconstructor para eliminar correctamente cualquier instancia dependiente
     */
    public void dispose() {
        thread = null;
        renderer = null;
        input = null;
    }
    
    // Getters
    public Config getConfig() {return config;}
    public Window getWindow() {return window;}
    public Input getInput() {return input;}
    public Renderer getRenderer() {return renderer;}
}
