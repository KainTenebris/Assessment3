package com.rear_admirals.york_pirates.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.rear_admirals.york_pirates.base.BaseActor;

public class PhysicsActor extends BaseActor {
	private Vector2 velocity;
	private Vector2 acceleration;

	private float maxSpeed;
	private float deceleration;

	private boolean autoAngle;
	private boolean anchor;

	//Constructor
	/**Initialises the physics actor which helps the ship to move with a velocity, acceleration, max speed, deceleration, anchor and auto-angle*/
	public PhysicsActor() {
		this.velocity = new Vector2();
		this.acceleration = new Vector2();
		this.maxSpeed = 9999;
		this.deceleration = 0;
		this.autoAngle = false;
		this.anchor = true;
	}
	
	//Getters
	/**Returns the boolean if the anchor is on or off
	 * @return anchor
	 */
	public boolean isAnchor() {
		return anchor; }
	/**Returns the speed of the actor
	 * @return velocity.len This is the speed of the vector
	 */
	public float getSpeed() {
		return velocity.len();}
	/**Returns the angle of the actor, what direction the ship is facing
	 * @return The angle of motion
	 */
	public float getMotionAngle() {
		return MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees; }

	//Setters
	/**Sets the deceleration.
	 * @param d A float representing deceleration value
	 */
	public void setDeceleration(float d) {
		deceleration = d;}
	/**Sets the acceleration of the actor in both the x and y plane.
	 * @param ax Acceleration in plane x
	 * @param ay Acceleration in plane y
	 */
	public void setAccelerationXY(float ax, float ay) {
		acceleration.set(ax, ay);}
	/**Increases the acceleration of the actor based on a given amount.
	 * @param amount The amount we want to increase acceleration by
	 * @param angle The current angle of acceleration
	 */
	public void addAccelerationAS(float angle, float amount) {
		acceleration.add(amount * MathUtils.cosDeg(angle),amount * MathUtils.sinDeg(angle)); }
	/**Sets the speed of the actor.
	 * @param s a float of the length of our velocity vector
	 */
		public void setSpeed(float s) {
		velocity.setLength(s);}
	/**Sets the max speed of the actor.
	 * @param ms The max speed value as a float
	 */
	public void setMaxSpeed(float ms) {
		maxSpeed = ms;}
	/**Sets the anchor as either true or false.
	 * @param anchor Boolean that is true if the anchor is down
	 */
	public void setAnchor(boolean anchor) {
		this.anchor = anchor; }
	/**Sets the acceleration of the actor in x and y directions based on the speed and angle
	 * @param speed The speed of the ship
	 * @param angleDeg The angle the ship is facing
	 */
	public void setAccelerationAS(float angleDeg, float speed) {
		acceleration.x = speed * MathUtils.cosDeg(angleDeg);
		acceleration.y = speed * MathUtils.sinDeg(angleDeg);
	}

	//handles acceleration/deceleration and image rotation
	/**Handles the acceleration/deceleration and image rotation of the actor
	 * @param dt The amount of time the key is pressed
	 */
	public void act(float dt) {
		super.act(dt);
		// apply acceleration
		velocity.add(acceleration.x * dt,acceleration.y * dt);
		// decrease velocity when not accelerating
		if (acceleration.len() < 0.01) {
			float decelerateAmount = deceleration * dt;
			if (getSpeed() < decelerateAmount) setSpeed(0);
			else setSpeed(getSpeed() - decelerateAmount);
		}
		// cap at max speed
		if (getSpeed() > maxSpeed) setSpeed(maxSpeed);
		// apply velocity
		moveBy(velocity.x * dt,velocity.y * dt);
		// rotate image when moving
		if (autoAngle && getSpeed() > 0.1) setRotation(getMotionAngle());
	}
}
