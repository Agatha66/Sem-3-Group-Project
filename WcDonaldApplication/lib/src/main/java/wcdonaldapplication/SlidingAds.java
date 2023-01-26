package wcdonaldapplication;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SlidingAds extends JPanel{ //for ads panel in HomePanel
	private JLabel circle1Label;
	private JLabel circle2Label;
	private JLabel circle3Label;
	private String circleIcon[] = {"/wcdonaldapplication/circle-black.png", "/wcdonaldapplication/circle-yellow.png"};
	private JPanel glassPanel;
	
	SlidingAds(int x, int y, int width, int height){
		setBounds(x, y, width, height);
		setLayout(null);
		
		glassPanel = new JPanel();
		glassPanel.setBounds(180, getHeight()- 31, 68, 26);
		glassPanel.setOpaque(false);
		
		circle1Label = new JLabel("");
		circle1Label.setBounds(0, 0, 16, 16);
		circle1Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[1]), 16, 16));
		glassPanel.add(circle1Label);
		
		circle2Label = new JLabel("");
		circle2Label.setBounds(0, 26, 16, 16);
		circle2Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
		glassPanel.add(circle2Label);
		
		circle3Label = new JLabel("");
		circle3Label.setBounds(0, 52, 16, 16);
		circle3Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
		glassPanel.add(circle3Label);
		
		add(glassPanel);
	}
	
	public void changeCircle(int AdsNo) {
		if(AdsNo == 0) {
			circle1Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[1]), 16, 16));
			circle2Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
			circle3Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
		}
		else if(AdsNo == 1) {
			circle1Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
			circle2Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[1]), 16, 16));
			circle3Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
		}
		else {
			circle1Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
			circle2Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[0]), 16, 16));
			circle3Label.setIcon(Main.resizeIcon(Main.class.getResource(circleIcon[1]), 16, 16));
		}
	}
	
	public void switchAds(JLabel hideAds, JLabel showAds, boolean animateLeft, int adsNo) {
		if(animateLeft) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						showAds.setLocation(getWidth(), 0);
						changeCircle(adsNo);
						for(int i = getWidth(); i >= 0; i-=6) {
							Thread.sleep(1);
							if(i >= 6) {
								hideAds.setLocation(i - hideAds.getWidth(), 0);
								showAds.setLocation(i, 0);
							}
							else {
								hideAds.setLocation(-hideAds.getWidth(), 0);
								showAds.setLocation(0, 0);
							}
						}
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}; t.start();
		}
		else {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						showAds.setLocation(-getWidth(), 0);
						changeCircle(adsNo);
						for(int i = 0; i <= getWidth(); i+=6) {
							Thread.sleep(1);
							if(i + 6 <= getWidth()) {
								hideAds.setLocation(i, 0);
								showAds.setLocation(i - showAds.getWidth(), 0);
							}
							else {
								hideAds.setLocation(hideAds.getWidth(), 0);
								showAds.setLocation(0, 0);
							}
						}
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}; t.start();
		}
	}
}
