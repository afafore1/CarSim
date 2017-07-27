package carsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarSim extends javax.swing.JFrame {
    final double ZOOM_SCALE_DELTA = 1.2;
    final double MIN_ZOOM = 0.08779149519;
    final double MAX_ZOOM = 17.0859375;
    final double FRAME_RATE = 60;
    final long INTERVAL = (long) (1000 / FRAME_RATE);
    final Color CLEAR_COLOR = Color.WHITE;
    final double ACCELERATION = 1;
    private int SENSOR1DISTANCE;
    private int SENSOR2DISTANCE;
    private int SENSOR3DISTANCE;

    
    
    // INTERVAL is what we use to set the simulation time.
    // FRAME_RATE is purely for readability.
    Car car;
    BufferedImage buffer;
    Graphics bufferGraphics;
    volatile boolean simulate = false;
    Transformation camTranform;
    double scale = 1;
    int prevX = -1, prevY = -1;
    boolean mouseDown = false;
    
    // Any object that needs to be rendered has to be added to this list.
    ArrayList<Renderable> renderableList = new ArrayList<>();
    // A list of obstacles to be given to the sensors.
    // Many sensors can share the same list.
    ArrayList<Obstacle> obstacleList = new ArrayList<>();
    
    Sensor sensor1, sensor2, sensor3;
    
    Algorithm algorithm;
    
    public CarSim() {
        initComponents();
        resize();
        camTranform = new Transformation();
        car = new Car(16, Math.toRadians(15), Math.toRadians(2),
                new Vector2D(40, 70), 50, .5, .06);
        car.setLocation(new Vector2D(140, -140));
        car.setRotation(Math.toRadians(0));
        renderableList.add(car);
        
        //front end, angle right, back end, angle left
        Obstacle o1 = new Obstacle(1850, -100, 330, -100);
        Obstacle o2 = new Obstacle(-400, -400, 1450, -350);
        Obstacle o3 = new Obstacle(1850, -100, 335, -300);
        renderableList.add(o1);
        renderableList.add(o2);
        renderableList.add(o3);
        obstacleList.add(o1);
        obstacleList.add(o2);
        obstacleList.add(o3);

        sensor1 = new Sensor(-8, 18, Math.toRadians(30), 200, car.carTransform);
        sensor2 = new Sensor(0, 20, 0, 200, car.carTransform);
        sensor3 = new Sensor(8, 18, Math.toRadians(-30), 200, car.carTransform);
        this.SENSOR3DISTANCE = 31;
        this.SENSOR2DISTANCE = 31;
        this.SENSOR1DISTANCE = 31;
        sensor1.obstacleList = obstacleList;
        sensor2.obstacleList = obstacleList;
        sensor3.obstacleList = obstacleList;
        renderableList.add(sensor1);
        renderableList.add(sensor2);
        renderableList.add(sensor3);
        
        startDrive();
        algorithm = new Algorithm(this);
    }

    public void startDrive() {
        Thread simulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long startTime = System.currentTimeMillis();
                    if (simulate) {
                        update();
                    }
                    try {
                        Thread.sleep(Math.max(0, INTERVAL - 
                                (System.currentTimeMillis() - startTime)));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CarSim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        simulationThread.start();
        simulate = true;  
    }
    
    public void stopDrive() {
        simulate = false;
    }
    // Frame update function.
    // Put your per frame logic here.
    void update() {
        // Your logic here:
        SENSOR1DISTANCE = (int) ((sensor1.getDistance() / sensor1.maxDistance)
                        * jProgressBar1.getMaximum());
        SENSOR2DISTANCE = (int) ((sensor2.getDistance() / sensor2.maxDistance)
                        * jProgressBar2.getMaximum());
        SENSOR3DISTANCE = (int) ((sensor3.getDistance() / sensor3.maxDistance)
                        * jProgressBar3.getMaximum());
        // My logic :P
        jProgressBar1.setValue(SENSOR1DISTANCE);
        jProgressBar2.setValue(SENSOR2DISTANCE);
        
        jProgressBar3.setValue(SENSOR3DISTANCE);
        
        // End of your logic.
        // Draw calls and updates.
        bufferGraphics.setColor(CLEAR_COLOR);
        bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        for (Renderable renderable : renderableList) {
            renderable.update();
            renderable.render(bufferGraphics, camTranform);
        }
        jPanel1.getGraphics().drawImage(buffer, 0, 0, jPanel1);
    }

    void resize() {
        buffer = new BufferedImage(jPanel1.getWidth(), jPanel1.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        bufferGraphics = buffer.getGraphics();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar2 = new javax.swing.JProgressBar();
        jProgressBar3 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jPanel1MouseWheelMoved(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel1MouseReleased(evt);
            }
        });
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized
        resize();
    }//GEN-LAST:event_jPanel1ComponentResized

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                car.acceleration = ACCELERATION;
                break;
            case KeyEvent.VK_DOWN:
                car.acceleration = -ACCELERATION;
                break;
            case KeyEvent.VK_SPACE:
                car.brake = true;
                break;
            case KeyEvent.VK_RIGHT:
                car.steeringTarget = - car.maxSteerAngle;
                break;
            case KeyEvent.VK_LEFT:
                car.steeringTarget = car.maxSteerAngle;
                break;
                case KeyEvent.VK_S:
                algorithm.simpleDrive();
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP: 
            case KeyEvent.VK_DOWN:
                car.acceleration = 0;
                break;
            case KeyEvent.VK_SPACE:
                car.brake = false;
                break;
            case KeyEvent.VK_RIGHT: 
            case KeyEvent.VK_LEFT:
                car.steeringTarget = 0;
                break;
        }
    }//GEN-LAST:event_formKeyReleased

    public void moveForward() {
        car.acceleration = ACCELERATION;
    }
    
    public void moveBackward() {
        car.acceleration = -ACCELERATION;
    }
    
    public void applyBrake(int degree) {
        try {
            car.brake = true;
            Thread.sleep((long)(degree/100));
            car.steeringTarget = 0;
            System.out.println("Moving right by "+degree+" degrees");
        } catch (InterruptedException ex) {
            Logger.getLogger(CarSim.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Applying brake by "+degree+" degrees");
    }
    
    public void moveRight(int degree) {
        try {
            car.steeringTarget = - car.maxSteerAngle;
            Thread.sleep((long)(degree/1000));
            car.steeringTarget = 0;
            System.out.println("Moving right by "+degree+" degrees");
        } catch (InterruptedException ex) {
            Logger.getLogger(CarSim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void moveLeft(int degree) {
        try {
            car.steeringTarget = car.maxSteerAngle;
            Thread.sleep((long)(degree/1000));
            car.steeringTarget = 0;
            System.out.println("Moving right by "+degree+" degrees");
        } catch (InterruptedException ex) {
            Logger.getLogger(CarSim.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Moving left by "+degree+" degrees");
    }
    
    public int getSENSOR1DISTANCE() {
        return SENSOR1DISTANCE;
    }
    
    public int getSENSOR2DISTANCE() {
        return SENSOR2DISTANCE;
    }
    
    public int getSENSOR3DISTANCE() {
        return SENSOR3DISTANCE;
    }
    
    private void jPanel1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jPanel1MouseWheelMoved
        double lastScale = scale;
        if (evt.getWheelRotation() < 0) {
            scale *= ZOOM_SCALE_DELTA;
        } else {
            scale /= ZOOM_SCALE_DELTA;
        }
        scale = CommonFunctions.clamp(scale, MIN_ZOOM, MAX_ZOOM);
        
        double deltaScale = scale / lastScale;
        Vector2D cursor = new Vector2D(evt.getX(), -evt.getY());
        Vector2D shiftVector = Vector2D.subtract(cursor, camTranform.origin)
                .multiply(deltaScale);
        camTranform.origin.moveTo(cursor).subtract(shiftVector);
        camTranform.setScale(scale);
    }//GEN-LAST:event_jPanel1MouseWheelMoved

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        if (mouseDown) {
            camTranform.shift(evt.getX() - prevX, -(evt.getY() - prevY));
            prevX = evt.getX();
            prevY = evt.getY();
        }
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        if (evt.getButton() == MouseEvent.BUTTON2) {
            mouseDown = true;
            prevX = evt.getX();
            prevY = evt.getY();
        }
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        if (evt.getButton() == MouseEvent.BUTTON2) {
            mouseDown = false;
        }
    }//GEN-LAST:event_jPanel1MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CarSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CarSim sim = new CarSim();
                sim.setLocationRelativeTo(null);
                sim.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JProgressBar jProgressBar3;
    // End of variables declaration//GEN-END:variables
}
