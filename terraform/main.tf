provider "aws" {
  region = var.aws_region
}

# ✅ Data sources pour récupérer automatiquement VPC et subnets
data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

# Security group pour le cluster EKS
resource "aws_security_group" "eks_cluster_sg" {
  name        = "eks-cluster-sg-${var.cluster_name}"
  description = "Security group for EKS cluster ${var.cluster_name}"
  vpc_id      = data.aws_vpc.default.id  # ✅ Utilise le VPC automatique
  
  ingress {
    from_port   = 8083
    to_port     = 8083
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  ingress {
    from_port   = 30000
    to_port     = 30000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  tags = {
    Name = "eks-cluster-sg-${var.cluster_name}"
  }
}

# Security group pour les workers EKS
resource "aws_security_group" "eks_worker_sg" {
  name        = "eks-worker-sg-${var.cluster_name}"
  description = "Security group for EKS worker nodes ${var.cluster_name}"
  vpc_id      = data.aws_vpc.default.id  # ✅ Utilise le VPC automatique
  
  ingress {
    from_port   = 8083
    to_port     = 8083
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  ingress {
    from_port   = 30000
    to_port     = 30000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  tags = {
    Name = "eks-worker-sg-${var.cluster_name}"
  }
}

# Le cluster EKS
resource "aws_eks_cluster" "my_cluster" {
  name     = var.cluster_name
  role_arn = var.role_arn
  version  = "1.30"
  
  vpc_config {
    subnet_ids              = data.aws_subnets.default.ids  # ✅ Utilise les subnets automatiques
    security_group_ids      = [aws_security_group.eks_cluster_sg.id]
    endpoint_public_access  = true
    endpoint_private_access = false
  }
  
  depends_on = [aws_security_group.eks_cluster_sg]
}

# Node group
resource "aws_eks_node_group" "my_node_group" {
  cluster_name    = aws_eks_cluster.my_cluster.name
  node_group_name = "noeud1"
  node_role_arn   = var.role_arn
  subnet_ids      = data.aws_subnets.default.ids  # ✅ Utilise les subnets automatiques
  
  scaling_config {
    desired_size = 2
    max_size     = 3
    min_size     = 1
  }
  
  depends_on = [aws_eks_cluster.my_cluster]
}

# ✅ Outputs (maintenant qu'outputs.tf n'existe plus, on peut les remettre ici)
output "cluster_name" {
  description = "EKS Cluster Name"
  value       = aws_eks_cluster.my_cluster.name
}

output "cluster_endpoint" {
  description = "EKS Cluster Endpoint"
  value       = aws_eks_cluster.my_cluster.endpoint
}

output "cluster_role_arn" {
  description = "EKS Cluster IAM Role ARN"
  value       = aws_eks_cluster.my_cluster.role_arn
}