#version 150

#moj_import <rect.glsl>

in vec4 vertexColor;
in vec2 vertexUV;
in vec2 texCoord0;

uniform vec2 Resolution;
uniform vec2 Center;
uniform float Radius;
uniform float Thickness;
uniform vec2 Size;
uniform float SmoothEdge;
uniform sampler2D Sampler0;
uniform vec4 RadiusSize;

out vec4 fragColor;

void main()
{
    vec2 st = (vertexUV - Center) * 2 / Resolution.y;
    vec2 size = Size / Resolution.y;
    float radius = Radius * RadiusSize[fetchIdx(st)] / Resolution.y;
    fragColor = texture(Sampler0, texCoord0) * mix(vec4(0), vertexColor, roundedRectangleSDF(st, size, radius, Thickness, SmoothEdge));
}