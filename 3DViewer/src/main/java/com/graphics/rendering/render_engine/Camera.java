package com.graphics.rendering.render_engine;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;


public class Camera {
    private Vector3D position;
    private Vector3D target;
    private final float fov;
    private float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

    public Camera(
            final Vector3D position,
            final Vector3D target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3D position) {
        this.position = position;
    }

    public void setTarget(final Vector3D target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getTarget() {
        return target;
    }

    public void movePosition(final Vector3D translation) {
        this.position.sumVectorThis(translation);
    }

    public void moveTarget(final Vector3D translation) {
        this.target.sumVector(translation);
    }

    Matrix4D getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4D getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }


}