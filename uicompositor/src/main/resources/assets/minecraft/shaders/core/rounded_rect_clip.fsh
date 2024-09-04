#version 150

in vec4 vertexColor;
in vec2 vertexUV;
in vec2 RawPosition;

uniform vec2 Resolution;
uniform vec2 Center;
uniform float Radius;
uniform float Thickness;
uniform vec2 Size;
uniform float SmoothEdge;
uniform sampler2D Sampler1;
uniform vec4 RadiusSize;

out vec4 fragColor;

float roundedRectangleSDF(vec2 p, vec2 a, float r)
{
    vec2 q = abs(p) - a + r;
    float d = length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
    return 1.0 - smoothstep(Thickness, Thickness + SmoothEdge, d);
}

void main()
{
    vec2 st = (vertexUV - Center) * 2 / Resolution.y;
    vec2 size = Size / Resolution.y;
    float radius = Radius * RadiusSize[int(step(0, st.x) + step(st.x, 0) * 2 + step(0, st.y) * 3 + step(st.y, 0) * 4 - 4)] / Resolution.y;
    fragColor = mix(vec4(0), vertexColor, roundedRectangleSDF(st, size, radius) * texture(Sampler1, RawPosition).a);
}