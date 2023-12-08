#version 150

in vec4 vertexColor;
in vec2 vertexUV;

uniform vec4 ColorModulator;
uniform vec2 Resolution;
uniform vec2 Center;
uniform float Radius;
uniform float Thickness;
uniform vec2 Size;
uniform sampler2D Sampler0;

out vec4 fragColor;

float roundedRectangle(vec2 offset, vec2 pos, vec2 size, float radius, float thickness)
{
    float d = length(max(abs(offset - pos), size) - size) - radius;
    return 1.0 - smoothstep(thickness, thickness + 0.01, d);
}

void main()
{
    vec2 npos = vertexUV.xy / Resolution.xy;
    float aspect = Resolution.x / Resolution.y;
    vec2 ratio = vec2(aspect, 1.0);
    vec2 uv = (2.0 * npos - 1.0) * ratio;

    vec2 size = Size / Resolution.x;
    vec2 pos = (2.0 * Center / Resolution - 1.0) * ratio;
    float radius = Radius / Resolution.x;
    float thickness = Thickness;

    float intensity = roundedRectangle(uv, pos, size, radius, thickness);
    fragColor = mix(vec4(0.0), vec4(vertexColor.xyz, 1.0), intensity * vertexColor.w);
}