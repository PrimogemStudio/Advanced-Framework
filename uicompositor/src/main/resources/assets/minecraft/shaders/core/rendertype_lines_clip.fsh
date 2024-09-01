#version 150

#moj_import <fog.glsl>

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform sampler2D Sampler1;

in float vertexDistance;
in vec4 vertexColor;
in vec2 RawPosition;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor * ColorModulator;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
    fragColor.a *= texture(Sampler1, RawPosition).a;
}
