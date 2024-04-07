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
    vec4 col = texture(BaseLayer, texCoord);
    vec4 dst = texture(DiffuseSampler, texCoord);
    /*if (col.a <= 0.01)
    {
        fragColor = dst * ColorModulate;
        return;
    }*/

    int scale = int(textureSize(BaseLayer, 0).x) / int(InSize.x);



    vec4 col0 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, -oneTexel.y) / 4);
    vec4 col1 = texture(BaseLayer, texCoord + vec2(0, -oneTexel.y) / scale);
    vec4 col2 = texture(BaseLayer, texCoord + vec2(oneTexel.x, -oneTexel.y) / scale);

    vec4 col3 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, oneTexel.y) / scale);
    vec4 col4 = texture(BaseLayer, texCoord + vec2(0, oneTexel.y) / scale);
    vec4 col5 = texture(BaseLayer, texCoord + vec2(oneTexel.x, oneTexel.y) / scale);

    vec4 col6 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, 0) / scale);
    vec4 col7 = texture(BaseLayer, texCoord);
    vec4 col8 = texture(BaseLayer, texCoord + vec2(oneTexel.x, 0) / scale);

    vec4 r = (col0 + col1 + col2 + col3 + col4 + col5 + col6 + col7 + col8) / 9;

    fragColor = mix(dst, vec4(r.rgb, 1.0), r.a);
}
