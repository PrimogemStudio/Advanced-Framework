#version 150

in vec4 Position;

uniform mat4 ProjMat;
uniform vec2 InSize;
uniform vec2 OutSize;
uniform vec4 RectPos;
uniform float Radius;

out vec2 texCoord;
out vec2 oneTexel;
out float isInRect;

void main() {
    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.2, 1.0);

    oneTexel = 1.0 / InSize;

    texCoord = Position.xy / OutSize;

    float x0 = RectPos[0];
    float x1 = RectPos[2];
    float y0 = RectPos[1];
    float y1 = RectPos[3];
    float xgt = texCoord[0];
    float ygt = texCoord[1];
    isInRect = step(4, step(x0, xgt) + step(xgt, x1) + step(y0, ygt) + step(ygt, y1)) * 0.25;
}
