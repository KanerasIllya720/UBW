package 테트리스;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class TetrisWindow extends JFrame implements ActionListener,KeyListener {
	TetrisBoard tb;
	Random rand = new Random();
	String[] ButtonName = { "게임시작", "블록교체", "블록회전", "게임종료" };
	JButton[] OrButton = new JButton[4];
	JLabel JL;

	// 3단계
	int[] BColor;
	int[][] TetrisMap = new int[20][10];
	int[][][] AllBlock;
	int[][] NBlock;
	int BlockNums, BlockX, BlockY;

	public TetrisWindow() {
		new JFXPanel();
		Media m = new Media("file:/g:/Koishi.mp3");
	    MediaPlayer p = new MediaPlayer(m);
	    p.play();
	    p.setVolume(0.1);
		this.setTitle("Tetris 0.01");
		this.setSize(500, 730);
		this.getContentPane().setBackground(new Color(0x00000000));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);

		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		// 판넬생성
		JPanel fp = new JPanel();
		fp.setPreferredSize(new Dimension(500, 40));
		fp.setBackground(new Color(0x001F1F20));
		fp.setOpaque(true);
		this.add(fp);
		// 버튼생성
		for (int i = 0; i < ButtonName.length; i++) {
			OrButton[i] = new JButton(ButtonName[i]);
			fp.add(OrButton[i]);
		}
		// 버튼 이벤트 처리
		for (int i = 0; i < 4; i++)
			this.OrButton[i].addActionListener(this);
		// 화면 그래픽 갱신
		this.repaint();
		this.revalidate();
		// 레이블생성
		JL = new JLabel("Score", JLabel.CENTER);
		JL.setPreferredSize(new Dimension(60, 25));
		JL.setBackground(new Color(0x00545966));
		JL.setOpaque(true);
		fp.add(JL);
	}

	void initialize() {
		// 7개 블록조각 색상
		this.BColor = new int[] { 0xFF0000, 0xFFA500, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF, 0xFF00FF };
		// 7개 블록조각 생성
		this.AllBlock = new int[][][] {
				{ { BColor[0], 0, 0, 0 }, { BColor[0], 0, 0, 0 }, { BColor[0], 0, 0, 0 }, { BColor[0], 0, 0, 0 } },
				{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { BColor[1], 0, 0, 0 }, { BColor[1], BColor[1], BColor[1], 0 } },
				{ { 0, 0, 0, 0 }, { BColor[2], 0, 0, 0 }, { BColor[2], 0, 0, 0 }, { BColor[2], BColor[2], 0, 0 } },
				{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { BColor[3], BColor[3], 0, 0 }, { BColor[3], BColor[3], 0, 0 } },
				{ { 0, 0, 0, 0 }, { BColor[4], 0, 0, 0 }, { BColor[4], BColor[4], 0, 0 }, { BColor[4], 0, 0, 0 } },
				{ { 0, 0, 0, 0 }, { BColor[5], 0, 0, 0 }, { BColor[5], BColor[5], 0, 0 }, { 0, BColor[5], 0, 0 } },
				{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { BColor[6], BColor[6], 0, 0 }, { 0, BColor[6], BColor[6], 0 } } };
		// 테트리스 게임판 초기화
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				this.TetrisMap[i][j] = 0;
			}
		}
		// 게임 변수 초기화
		this.BlockNums = 0;
		this.NBlock = AllBlock[BlockNums].clone();
		this.BlockX = 3;
		this.BlockY = 0;
	}

	void drawTetrisBoard(int Blocknums, int x, int y) {
		this.BlockNums = Blocknums;
		this.NBlock = this.AllBlock[this.BlockNums].clone();
		this.BlockX = x;
		this.BlockY = y;

		tb.repaint();
		tb.revalidate();
	}
	
	void moveTetrisBlock(int x,int y) {
		this.BlockX+=x;
		this.BlockY+=y;
		drawTetrisBoard(this.BlockNums, this.BlockX, this.BlockY);
	}
	void rotateTetrisBlock() {
		int[][] RotateBlock = new int[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++) 
				RotateBlock[j][3-i]=this.NBlock[i][j];
		NBlock=RotateBlock;
		tb.repaint();
		tb.revalidate();
	}

	public void actionPerformed(ActionEvent act) {
		JButton jb = (JButton) act.getSource();
		if (jb.getText().equals("게임시작")) {
			try {
				this.removeKeyListener(this);
			} catch (Exception e) {
			}
			this.addKeyListener(this);
			this.requestFocus();
		}
			
		else if (jb.getText().equals("블록교체")) {
			this.BlockNums = rand.nextInt(7);
			this.NBlock = this.AllBlock[this.BlockNums].clone();
			drawTetrisBoard(this.BlockNums, this.BlockX, this.BlockY);
			this.requestFocus();
		} else if (jb.getText().equals("블록회전")) {

		}
			
		else if (jb.getText().equals("게임종료"))
			;
	}
	
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			moveTetrisBlock(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			moveTetrisBlock(1,0);
			break;
		case KeyEvent.VK_UP:
			this.rotateTetrisBlock();
			this.requestFocus();
			break;
		case KeyEvent.VK_DOWN:
			moveTetrisBlock(0,1);
			break;
		case KeyEvent.VK_SPACE:
			break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}
}
