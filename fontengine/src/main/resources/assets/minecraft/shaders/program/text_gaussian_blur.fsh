#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D BaseLayer;
uniform sampler2D ClipSampler;

uniform vec4 ColorModulate;
uniform vec2 InSize;
uniform int CalcSize;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main() {
    vec4 dst = texture(DiffuseSampler, texCoord);

    int scale = int(textureSize(BaseLayer, 0).x) / int(InSize.x);
    vec4 r = vec4(0);
    float vert = CalcSize * 2 - 1;
    vert *= vert;
    int st = CalcSize - 1;

    for (int xadd = -st; xadd <= st; xadd++) {
        for (int yadd = -st; yadd <= st; yadd++) {
            r += texture(BaseLayer, texCoord + vec2(xadd * oneTexel.x, yadd * oneTexel.y) / scale) / vert;
        }
    }

    fragColor = mix(dst, vec4(r.rgb, 1.0), r.a * texture(ClipSampler, texCoord).a);
}
