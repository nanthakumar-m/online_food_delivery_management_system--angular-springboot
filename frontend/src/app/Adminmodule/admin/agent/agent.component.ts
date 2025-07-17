import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Agent } from '../models/agent.model';
import { AdminAgentService } from '../services/agent.service';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-agent',
  standalone:true,
  imports: [CommonModule,HttpClientModule,RouterModule,NavbarComponent],
  templateUrl: './agent.component.html',
  styleUrl: './agent.component.css'
})
export class AdminAgentComponent implements OnInit{
  agents:Agent[]=[];

  constructor(private agentService:AdminAgentService,private router:Router){}

  ngOnInit(): void {
    this.agentService.getAllAgents().subscribe(data=>{
      this.agents=data;
    })
  }

  deleteRestaurant(agentId:number){
    this.agentService.deleteAgent(agentId).subscribe({
      next:(response)=>{
        alert("Agent Deleted Successfully")
        this.agents=this.agents.filter(agent=>agent.agentId !==agentId)
        
      }
    })
  }
}
