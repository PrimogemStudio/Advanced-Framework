#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D TextBuffer;
uniform vec4 ColorModulate;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main() {
    vec4 col = texture(TextBuffer, texCoord);
    vec4 dst = texture(DiffuseSampler, texCoord);

    fragColor = mix(dst, vec4(col.rgb, 1), col.a);
}
