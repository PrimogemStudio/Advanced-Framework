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

    vec4 col0 = texture(BaseLayer, texCoord + vec2(2 * -oneTexel.x, 2 * -oneTexel.y) / 4);
    vec4 col1 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, 2 * -oneTexel.y) / 4);
    vec4 col2 = texture(BaseLayer, texCoord + vec2(0, 2 * -oneTexel.y) / scale);
    vec4 col3 = texture(BaseLayer, texCoord + vec2(oneTexel.x, -oneTexel.y) / scale);
    vec4 col4 = texture(BaseLayer, texCoord + vec2(2 * oneTexel.x, 2 * -oneTexel.y) / scale);

    vec4 col5 = texture(BaseLayer, texCoord + vec2(2 * -oneTexel.x, -oneTexel.y) / 4);
    vec4 col6 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, -oneTexel.y) / 4);
    vec4 col7 = texture(BaseLayer, texCoord + vec2(0, -oneTexel.y) / scale);
    vec4 col8 = texture(BaseLayer, texCoord + vec2(oneTexel.x, -oneTexel.y) / scale);
    vec4 col9 = texture(BaseLayer, texCoord + vec2(2 * oneTexel.x, -oneTexel.y) / scale);

    vec4 col10 = texture(BaseLayer, texCoord + vec2(2 * -oneTexel.x, oneTexel.y) / scale);
    vec4 col11 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, oneTexel.y) / scale);
    vec4 col12 = texture(BaseLayer, texCoord + vec2(0, oneTexel.y) / scale);
    vec4 col13 = texture(BaseLayer, texCoord + vec2(oneTexel.x, oneTexel.y) / scale);
    vec4 col14 = texture(BaseLayer, texCoord + vec2(2 * oneTexel.x, oneTexel.y) / scale);

    vec4 col15 = texture(BaseLayer, texCoord + vec2(2 * -oneTexel.x, 0) / scale);
    vec4 col16 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, 0) / scale);
    vec4 col17 = texture(BaseLayer, texCoord);
    vec4 col18 = texture(BaseLayer, texCoord + vec2(oneTexel.x, 0) / scale);
    vec4 col19 = texture(BaseLayer, texCoord + vec2(2 * oneTexel.x, 0) / scale);

    vec4 col20 = texture(BaseLayer, texCoord + vec2(2 * -oneTexel.x, 2 * oneTexel.y) / 4);
    vec4 col21 = texture(BaseLayer, texCoord + vec2(-oneTexel.x, 2 * oneTexel.y) / 4);
    vec4 col22 = texture(BaseLayer, texCoord + vec2(0, 2 * oneTexel.y) / scale);
    vec4 col23 = texture(BaseLayer, texCoord + vec2(oneTexel.x, oneTexel.y) / scale);
    vec4 col24 = texture(BaseLayer, texCoord + vec2(2 * oneTexel.x, 2 * oneTexel.y) / scale);

    vec4 r = (col0 + col1 + col2 + col3 + col4 + col5 + col6 + col7 + col8 + col9 + col10 + col11 + col12 + col13 + col14 + col15 + col16 + col17 + col18 + col19 + col20 + col21 + col22 + col23 + col24) / 25;

    fragColor = mix(dst, vec4(r.rgb, 1.0), r.a);
}
