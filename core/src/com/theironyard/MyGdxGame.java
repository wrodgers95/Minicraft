package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {

	float x, y, xv, yv;
	static final float MAX_VELOCITY = 500;

	SpriteBatch batch;
	public TextureRegion left, right, up, down;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	static final int DRAW_WIDTH = WIDTH*3;
	static final int DRAW_HEIGHT = HEIGHT*3;

	boolean faceRight;
	boolean faceUp;
	boolean faceDown;
	boolean faceLeft;



	@Override
	public void create() {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);

		left.flip(true, false);
	}

	@Override
	public void render() {

		move();

		if (y > Gdx.graphics.getHeight()){
			y = 0;
		}
		if (y < 0 ){
			y = Gdx.graphics.getHeight();
		}
		if (x < 0 ){
			x = Gdx.graphics.getWidth();
		}
		if (x > Gdx.graphics.getWidth()){
			x = 0;
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		if (faceUp) {

			batch.draw(up, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		} else if (faceDown) {

			batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		} else if (faceRight) {

			batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		} else if (faceLeft) {

			batch.draw(left, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}

		batch.end();

	}

	float decelerate(float velocity) {
		float deceleration = 0.95f;
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			faceUp = true;
			faceLeft = false;
			faceDown = false;
			faceRight = false;
			yv = MAX_VELOCITY;
			if (Gdx.input.isKeyPressed(Input.Keys.UP) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				yv = MAX_VELOCITY * 2;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			faceDown = true;
			faceUp = false;
			faceLeft = false;
			faceRight = false;
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				yv = MAX_VELOCITY *-2;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			faceRight = true;
			faceUp = false;
			faceLeft = false;
			faceDown = false;

			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				xv = MAX_VELOCITY *2;

			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY *-1;
			faceLeft = true;
			faceUp = false;
			faceDown = false;
			faceRight = false;

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				xv = MAX_VELOCITY *-2;
			}
		}

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
