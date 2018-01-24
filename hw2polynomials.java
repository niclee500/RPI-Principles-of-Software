Polynomial Subtraction:
Precondition: p and q are non-empty arrays, r is an array of length max(p.length, q.length)
d = 0;
while ( d != max(p.length, q.length) )
  Invariant: r[i] == (p-q)[i] foreach 0 <= i < d
{
  if (d < p.length && d < q.length) { 
    r[d] = p[d] - q[d];
  } else if (d < p.length) { 
    r[d] = p[d];
  } else {
    r[d] = -q[d];
  } 
  d = d + 1;
}
Postcondition: r[i] == (p-q)[i] foreach 0 <= i < r.length


Polynomial Multiplication:
Precondition: p and q are non-empty arrays, r is an array of length (p.length + q.length - 1) initialized to all zeros
i = 0;
while ( i != p.length )
  Invariant: ???
{
  j = 0;
  while( j != q.length )
    Invariant: ???
  {
    r[i+j] = r[i+j] + (p[i] * q[j]);
    j = j + 1;
  }
  i = i + 1;
}
Postcondition: ???

/*
To evaluate a polynomial, say 2*x^4 + 3*x^3 - 6*x^2 + 2*x + 1 for given x, we may first compute x^4, 
then 3*x^3 and add it to the running sum, etc. This clearly requires recomputation of the powers 
of x. An alternative is to store the powers, but this requires extra space. Horner's rule is a 
technique which avoids recomputation and extra space. It alternates between multiplication and 
addition and allows a degree d polynomial to be evaluated using d additions and d multiplications.
Our example polynomial can be written as x(x(x(2*x + 3) - 6) + 2) + 1. According to Horner's rule, 
computation proceeds as follows: evaluate 2*x + 3 first, then multiply the result by x, then 
subtract 6, etc. Your evaluation algorithm should implement Horner's rule.
*/
Horner Polynomial Evaluation:
Precondition: a is a non-empty array representation of a polynomial, x is some given integer
ord = a.length-1;
i = ord;
itr = 0;
val = a[i];
while( i > 0 )
  Invariant: i >= 0 && itr == ord-i && val == a[i]*x^0 + ... + a[ord]*x^itr
{
  val = val * x + a[i-1];
  i = i - 1;
  itr = itr + 1;
}
Postcondition: val == a[0]*x^0 + ... + a[a.length-1]*x^(a.length-1)


/*
at beginning: val = 2
after 1st itr: val = 2*x + 3
after 2nd itr: val = 2*x^2 + 3*x - 6
after 3rd itr: val = 2*x^3 + 3*x^2 - 6*x + 2
after 4th itr: val = 2*x^4 + 3*x^3 - 6*x^2 + 2*x + 1
*/











