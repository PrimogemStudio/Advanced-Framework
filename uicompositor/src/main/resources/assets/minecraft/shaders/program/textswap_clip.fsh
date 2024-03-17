#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D TextSwapSampler;
uniform sampler2D ClipSampler;

uniform vec4 ColorModulate;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main() {
    vec4 a = texture(TextSwapSampler, texCoord);
    fragColor = mix(texture(DiffuseSampler, texCoord), vec4(a.rgb, 1.0), a.a * texture(ClipSampler, texCoord).a);
}