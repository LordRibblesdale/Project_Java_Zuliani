import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class TesoroMiSiSonoRistrettiIPG extends JFrame {
   class ActionP extends AbstractAction {
      ActionP(String in) {
         putValue(Action.NAME, in);
      }

      public void actionPerformed(ActionEvent e) {
         switch ((String) getValue(Action.NAME)) {
            case "Apri File":
               JFileChooser f = new JFileChooser();
               f.setAcceptAllFileFilterUsed(false);
               f.addChoosableFileFilter(new FileFilter() {
                  private final String jpg = "jpeg";
                  private final String png = "png";

                  public boolean accept(File pathname) {
                     String s = pathname.getName();
                     int i = s.lastIndexOf(".");

                     if (i != -1) {
                        String e = s.substring(i+1, s.length());

                        return e.equalsIgnoreCase(jpg) || e.equalsIgnoreCase(png);
                     }

                     return pathname.isDirectory();
                  }

                  public String getDescription() {
                     return "*.jpg, *.png";
                  }
               });

               int r = f.showOpenDialog(TesoroMiSiSonoRistrettiIPG.this);

               if (r == JFileChooser.APPROVE_OPTION) {
                  try {
                     image = ImageIO.read(f.getSelectedFile());
                  } catch (IOException ex) {
                     ex.printStackTrace();
                  }

                  editRGB(image);
                  p.repaint();
                  setSize(edit.getWidth(), edit.getHeight());
                  validate();
               }
               break;
            case "Ingrandisci":
               editRGB(image, 'i');
               p.repaint();
               setSize(edit.getWidth(), edit.getHeight());
               validate();
               break;
            case "Rimpicciolisci":
               editRGB(image, 'r');
               p.repaint();
               setSize(edit.getWidth(), edit.getHeight());
               validate();
               break;
         }
      }
   }

   protected JToolBar toolBar;
   private JPanel p;
   private BufferedImage image, edit;

   TesoroMiSiSonoRistrettiIPG() {
      toolBar = new JToolBar();
      toolBar.add(new ActionP("Apri File"));
      toolBar.add(new ActionP("Ingrandisci"));
      toolBar.add(new ActionP("Rimpicciolisci"));

      p = new JPanel() {
         public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image != null && edit != null) {
               g.drawImage(edit, 0, 0, null);
            }
         }
      };

      add(toolBar, BorderLayout.PAGE_START);
      add(p);

      setResizable(false);
      setSize(500, 700);
      setLocationRelativeTo(null);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   protected void editRGB(BufferedImage img) {
      edit = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

      for (int i = 0; i < edit.getWidth(); i++) {
         for (int j = 0; j < edit.getHeight(); j++) {
            edit.setRGB(i, j, (new Color(new Color(img.getRGB(i, j)).getRed(), new Color(img.getRGB(i, j)).getGreen(), new Color(img.getRGB(i, j)).getBlue())).getRGB());
         }
      }
   }

   protected void editRGB(BufferedImage img, char mode) {
      if (mode == 'i') {
         edit = new BufferedImage(img.getWidth()*2, img.getHeight()*2, BufferedImage.TYPE_INT_ARGB);


         for (int i = 0, h = 0; i < edit.getWidth(); i += 2, h++) {
            for (int j = 0, k = 0; j < edit.getHeight(); j += 2, k++) {
               int rgb = img.getRGB(h, k);

               edit.setRGB(i, j, rgb);
               edit.setRGB(i+1, j, rgb);
               edit.setRGB(i, j+1, rgb);
               edit.setRGB(i+1, j+1, rgb);
            }
         }
      } else if (mode == 'r') {
         edit = new BufferedImage(img.getWidth()/2, img.getHeight()/2, BufferedImage.TYPE_INT_ARGB);

         for (int i = 0, h = 0; i < edit.getWidth(); i++, h += 2) {
            for (int j = 0, k = 0; j < edit.getHeight(); j++, k += 2) {
               int red = (new Color(img.getRGB(h, k)).getRed() + new Color(img.getRGB(h+1, k)).getRed() + new Color(img.getRGB(h, k+1)).getRed() + new Color(img.getRGB(h+1, k+1)).getRed())/4;
               int green = (new Color(img.getRGB(h, k)).getGreen() + new Color(img.getRGB(h+1, k)).getGreen() + new Color(img.getRGB(h, k+1)).getGreen() + new Color(img.getRGB(h+1, k+1)).getGreen())/4;
               int blue = (new Color(img.getRGB(h, k)).getBlue() + new Color(img.getRGB(h+1, k)).getBlue() + new Color(img.getRGB(h, k+1)).getBlue() + new Color(img.getRGB(h+1, k+1)).getBlue())/4;
               edit.setRGB(i, j, (new Color(red, green, blue)).getRGB());
            }
         }
      }
   }

   public static void main(String[] args) {
      TesoroMiSiSonoRistrettiIPG t = new TesoroMiSiSonoRistrettiIPG();
   }
}
