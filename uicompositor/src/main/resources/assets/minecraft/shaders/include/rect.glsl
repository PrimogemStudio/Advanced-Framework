#version 150

int fetchIdx(vec2 st)
{
    return int(step(0, st.x) + step(st.x, 0) * 2 + step(0, st.y) * 5 + step(st.y, 0) * 3 - 4);
}

float roundedRectangleSDF(vec2 p, vec2 a, float r, float t, float sme)
{
    vec2 q = abs(p) - a + r;
    float d = length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
    return 1.0 - smoothstep(t, t + sme, d);
}