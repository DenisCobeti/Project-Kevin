package engine2D;

/**
 * Clase que contiene el hilo del motor sobre el que se ejecuta el juego.
 * Cuenta con todos los parametros de las clases del paquete.
 * 
 * @author Project Kevin
 */
public class GameContainer implements Runnable {
    private final double UPDATE_CAP = 1.0/60.0; // min. 1/25 - max. 1/60

    // Variables de actualización
    private double deltaTime = 0;
    private double unprocessedTime = 0;
    
    // Variables de monitorización
    public double ups = 0;
    public double fps = 0;
    
    private final Config config;
    
    // Herramientas del motor
    private Thread thread;
    private final Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;
    
    private boolean running = false;

    /**
     * Constructor de la clase
     */
    public GameContainer() {
        config = Config.getInstance();
        window = new Window(this);
    }
    
    /**
     * Crea las instancias restantes del motor y lanza su thread
     * @param game Juego que hace uso del motor gráfico
     */
    public void start(AbstractGame game) {
        this.game = game;
        window.execGame();
        renderer = new Renderer(window);
        renderer.setBackground(game.getBackground());
        input = new Input(window.getCanvas());
        
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * Se para totalmete el motor
     */
    public void stop() {
        running = false;
    }
    
    @Override
    public void run() {
        running = true;
        
        // Variables básicas
        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1e9d;
        double passedTime = 0;
        //double unprocessedTime = 0;
        
        // Variables de monitorización
        double frameTime = 0;
        int frames = 0;
        int updates = 0;
   
        // Bucle principal del motor gráfico
        while(running) {
            render = false;
            
            firstTime = System.nanoTime() / 1e9d;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            
            unprocessedTime += passedTime;
            frameTime += passedTime;
            
            // Se llevan a cabo tantas actualizaciones como sean necesarias
            while(unprocessedTime >= deltaTime && deltaTime != 0) {
                unprocessedTime -= deltaTime;
                render = true;
                
                game.update(this, (float)deltaTime);
                
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
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1); // Se evita saturar al TaskManager
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        dispose();
    }
    
    /**
     * Se pausa el motor
     */
    public void pause() {
        if (running) deltaTime = 0;
    }
    
    /**
     * Si el motor estaba pausado se reanuda su ejecución
     */
    public void resume() {
        deltaTime = UPDATE_CAP;
        unprocessedTime = 0;
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
    public AbstractGame getGame() {return game;}
    public Window getWindow() {return window;}
    public Input getInput() {return input;}
    public Renderer getRenderer() {return renderer;}
}
