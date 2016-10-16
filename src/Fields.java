
import java.util.ArrayList;
import java.util.List;

import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;

public class Fields {
	private static final int LEFTWIDTH = 55; // ���ݵؿ鿪ʼ��x����
	private static final int RIGHTWIDTH = 1610;// �Ҳ�ݵؿ鿪ʼ��x����
	private static final int FENCEHIGHT = 75;
	public static final int FIELDWIDTH = 85; // �ݵؿ�֮��ļ��
	public static final int FIELDHIGHT = 100;
	private int t = 0;
	private Field f = null;
	private Main main = null;
	private List<Field> allFields = new ArrayList<Field>();

	public List<Field> getAllField() {
		return allFields;
	}

	public Fields(Main main) {
		// ��ʼ���ݵؼ���
		this.main = main;
		boolean isLeft = Main.isLeft;
		int s=0;
		for (int j = 0; j < 5; j++) {
			s++;
			for (int i = 0; i < 9; i++) {
			Field f=new Field(t, (i * 82) + LEFTWIDTH,
						FENCEHIGHT + (j * 100), isLeft, this);
			f.setRowID(s);
				this.allFields.add(f);
				t++;
				
			}
		}
		int w=0;
		for (int j = 0; j < 5; j++) {
			w++;
			for (int i = 0; i < 9; i++) {
			Field f=new Field(t, (i * 82) + RIGHTWIDTH,
						FENCEHIGHT + (j * 100), !isLeft, this);
				f.setRowID(w);
			this.allFields.add(f);
				t++;

			}
		}		
		
	}

	public Main getMain() {
		return main;
	}

	public void showInformation() {

	}

	// �õ���ǰ������ڵĲݵؿ���
	public int getRange(int x, int y) {
		for (int i = 0; i < allFields.size(); i++) {
			f = allFields.get(i);
			if (f.getX() <= x && x <= f.getX() + FIELDWIDTH && y >= f.getY()
					&& y < f.getY() + FIELDHIGHT) {
				return f.getId();
			}
		}
		return -1;
	}

	// ���ݵص�Ӱͼ��
	public void drawDImage(int cfRange, int filedID, int mpx, LGraphics g) {
		int cardID = CardsFrame.cardRangeID[cfRange];
		int objectID = main.cf.allCards.get(cfRange).getObjectID();
		LImage image = null;
		if (objectID > 300) {
			int  animationID=Resource.getZombieInfo(objectID-300)[3];
			image = Resource.getZombieAnimation(animationID).getSpriteImage(0).getLImage();
		} else {
			int animationID = Resource.getPlantInfo(objectID)[4];
			image = Resource.getPlantAnimation(animationID).getSpriteImage(0).getLImage();
		}

		int width, hight;
		width = image.getWidth();
		hight = image.getHeight();
		int x = allFields.get(filedID).getCx() - width / 2;
		x += mpx;
		int y = allFields.get(filedID).getCy() - hight / 2;
		int[] i ;
		if(objectID<=300){
			 i = Resource.getPlantInfo(objectID);
		}else{
			i=Resource.getZombieInfo(objectID-300);
		}
			int xr;
			int yr;
			if(Main.isLeft){
				xr=i[12];	
			}else{
				xr=-i[12];
			}
			yr=i[13];	
			x+=xr;
			y+=yr;			
		
		double d = g.getAlpha();
		g.setAlpha(0.4f);
		// ��ߣ��ұ߻���ͬ�ľ���ͼ�� �������ֲ���ͼ���෴
		if (main.isLeft) {
				g.drawImage(image, x, y);
		} else {
				g.drawMirrorImage(image, x, y);	
		}

		g.setAlpha(d);
	}

	
	//ȡ�����е�������
	public  void redoAllClick(){
		for(Field f : this.allFields){
			 f.redoClick();	
		}
		main.clickObject=false;
	}
	
	
}
