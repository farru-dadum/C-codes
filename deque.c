#include<stdio.h>
void main()
{
int a[50] , n , ch , r , f;
int rear = 0 , front = 0;
printf("Enter the Number of Elements in Queue");
scanf("%d",&n);
printf("Enter Your Choice");
printf(" 1. Insert Rear \n 2.Delete Rear \n 3.Insert Front \n 4.Delete Front \n 5.Display \n 6.Exit");
scanf("%d",&ch);
while( i != 1)
{
if ( ch == 1 )
{
   if ( (rear+1)%n == front)
   {
   printf("Queue Is Overflown");
   }
   else
   { 
   printf("Enter the Value to be Inserted");
   scanf("%d",&r);
   rear = (rear+1)%n;
   a[rear] = r;
   }
}
else if ( ch == 2)
{ 
  if(front == rear)
  {
  printf("Queue is Empty");
  }
  else if( rear == 0)
  {
  r = a[rear];
  rear = n - 1;
  }
  else
  {
  r = a[rear];
  rear = (rear - 1)%n; 
  }
}
else if ( ch == 3 )
{ 
   printf("Enter the Element to be inserted at Front");
   scanf("%d",&f); 
   if ( (rear+1)%n == front)
   {
   printf("Queue Is Overflown");
   }
   else if ( front == 0)
   {
    a[front] = f;
    front = n - 1;
   }
   else
   {
   a[front] = f;
   front = (front-1)%n;
   } 
}

else if ( ch == 4)
{ 
    if  (rear == front)
   {
   printf("Queue Is Empty");
   }
   else
   { 
   front = (front+1)%n;
   f = a[front];
   }
}
else if ( ch == 5)
{ 
   if ( front == rear)
   {
   printf("Queue is Empty");
   }
   else
   {
    int q ;
    q = (front + 1)%n;
    while( q <= rear)
    {
    printf("%d\n",a[q]);
    q = (q+1)%n;
    }
   }
}
else if ( ch == 6)
{ 
   printf("Exitted From Program");
   i ++ ;
}
else
{
printf("Invalid choice");
}
}
}


    