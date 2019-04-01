package engine2D;

/**
 * Clase que contiene el hilo del motor sobre el que se ejecuta el juego.
 * Cuenta con todos los parametros de las clases del paquete.
 * 
 * @author Project Kevin
 */
public class GameContainer implements Runnable {
    
    private final double UPDATE_CAP = 1.0/60.0; // deltaTime min. 1/25 max. 1/60
    
    public final int SCREEN_WIDTH = 1360;
    public final int SCREEN_HEIGHT = 768;
    public final float SCALE = 1f;
    
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
        window = new Window(this);
    }
    
    /**
     * Crea las instancias de los objetos del motor y lanza su thread
     */
    public void start() {
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
        
        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1e9d;
        double passedTime = 0;
        double unprocessedTime = 0;
        
        double frameTime = 0;
        int frames = 0;
        int fps = 0;
   
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
                    frames = 0;
                    System.out.println("fps: " + fps);
                }
            }
            
            // Se llama al renderizador para que dibuje en pantalla
            if(render) {
                renderer.clear();
                game.render(this, renderer);
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
    public Window getWindow() {return window;}
    public Input getInput() {return input;}
    public Renderer getRenderer() {return renderer;}
}
