#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;
in float isInRect;

uniform vec2 InSize;

uniform vec4 RectPos;
uniform float Radius;

out vec4 fragColor;

void main() {
    float x0 = RectPos[0];
    float x1 = RectPos[2];
    float y0 = RectPos[1];
    float y1 = RectPos[3];
    float xgt = texCoord[0];
    float ygt = texCoord[1];
    fragColor = mix(texture(DiffuseSampler, texCoord), vec4(0.0, 0.0, 0.0, Radius), step(4, step(x0, xgt) + step(xgt, x1) + step(y0, ygt) + step(ygt, y1)) * 0.25);
}
