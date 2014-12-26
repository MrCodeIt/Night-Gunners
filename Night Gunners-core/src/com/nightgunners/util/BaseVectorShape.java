package com.nightgunners.util;

import java.awt.Shape;

// * Class for all shapes * \\
public class BaseVectorShape {
	
	
	
	public BaseVectorShape() {//empties values by default so they must be initialized
		setShape(null);
		setAlive(false);
		setX(0.0);
		setY(0.0);
		setVelX(0.0);
		setVelY(0.0);
		setMoveAngle(0.0);
		setFaceAngle(0.0);
	}
	
	
	
	// * Variables * \\
	private Shape shape;
	private boolean alive;
	private double x,y;
	private double velX,velY;
	private double moveAngle, faceAngle;
	
	
	
	// * Accessor methods * \\
	public Shape getShape() {return shape;}                                  //returns shape value
	public boolean isAlive() {return alive;}                                 //returns alive value
	
	public double getX() {return x;}                                         //returns x value
	public double getY() {return y;}                                         //returns y value
	
	public double getVelX() {return velX;}                                   //returns velX value
	public double getVelY() {return velY;}                                   //returns velY value
	
	public double getMoveAngle() {return moveAngle;}                         //returns moveAngle value
	public double getFaceAngle() {return faceAngle;}                         //returns faceAngle value
	
	
	
	// * Mutator and helper methods * \\
	public void setShape(Shape shape)   {this.shape = shape;}                //sets shape value
	public void setAlive(boolean alive) {this.alive = alive;}                //sets alive value
	
	public void setX(double x) {this.x = x;}                                 //sets x value
	public void incX(double i) {this.x += i;}                                //increases x value by i
	public void setY(double y) {this.y = y;}                                 //sets y value
	public void incY(double i) {this.y += i;}                                //increases y value by i
	
	public void setVelX(double velX) {this.velX = velX;}                     //sets velX value
	public void incVelX(double i   ) {this.velX += i;}                       //increases velX value by i
	public void setVelY(double velY) {this.velY = velY;}                     //sets velY value
	public void incVelY(double i   ) {this.velY += i;}                       //increases valY value by i
	
	public void setMoveAngle(double moveAngle) {this.moveAngle = moveAngle;} //sets moveAngle value
	public void incMoveAngle(double i) {this.moveAngle += i;}                //increases moveAngle value by i
	public void setFaceAngle(double faceAngle) {this.faceAngle = faceAngle;} //sets faceAngle value
	public void incFaceAngle(double i) {this.faceAngle += i;}                //increases faceAngle value by i
	
}