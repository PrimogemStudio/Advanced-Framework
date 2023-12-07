#version 150

in vec4 vertexColor;
in vec4 vertexPos;

uniform vec4 ColorModulator;

out vec4 fragColor;

float roundedRectangle(vec2 offset, vec2 pos, vec2 size, float radius, float thickness)
{
    float d = length(max(abs(offset - pos), size) - size) - radius;
    return 1.0 - smoothstep(thickness, thickness + 0.01, d);
}

void main()
{
    vec3 col = vec3(0.0);
    vec2 pos = vec2(0.0, 0.0);
    vec2 size = vec2(0.2, 0.2);
    float radius = 0.1;
    float thickness = 0.012;

    const vec3 rectColor = vec3(0.1, 0.3, 0.2);
    float intensity = roundedRectangle(vertexPos.xy, pos, size, radius, thickness);
    col = mix(col, rectColor, intensity);
    fragColor = vec4(col, 1);
}