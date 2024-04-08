#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D BaseLayer;

uniform vec4 ColorModulate;
uniform int Radius;
uniform int DigType;
uniform vec2 InSize;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

#define CORE_RAD 3
void main() {
    vec4 dst = texture(DiffuseSampler, texCoord);

    int scale = int(textureSize(BaseLayer, 0).x) / int(InSize.x);
    vec4 r = vec4(0);
    float vert = CORE_RAD * 2 - 1;
    vert *= vert;
    int st = CORE_RAD - 1;

    for (int xadd = -st; xadd <= st; xadd++) {
        for (int yadd = -st; yadd <= st; yadd++) {
            r += texture(BaseLayer, texCoord + vec2(xadd * oneTexel.x, yadd * oneTexel.y) / scale) / vert;
        }
    }

    fragColor = mix(dst, vec4(r.rgb, 1.0), r.a);
}
