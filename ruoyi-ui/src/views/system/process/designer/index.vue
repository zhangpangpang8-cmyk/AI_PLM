<template>
  <div class="app-container process-designer">
    <div class="designer-header">
      <el-page-header @back="goBack" content="流程设计器"></el-page-header>

      <el-card shadow="never" class="toolbar-card" style="margin-top: 15px;">
        <div class="toolbar">
          <div class="toolbar-left">
            <el-button-group>
              <el-button size="small" icon="el-icon-circle-plus" @click="addNode('startEvent')">
                开始节点
              </el-button>
              <el-button size="small" icon="el-icon-user-solid" @click="addNode('userTask')">
                审批节点
              </el-button>
              <el-button size="small" icon="el-icon-circle-close" @click="addNode('endEvent')">
                结束节点
              </el-button>
            </el-button-group>

            <el-divider direction="vertical"></el-divider>

            <el-button-group>
              <el-button size="small" icon="el-icon-link" @click="startConnect" :disabled="!selectedNode">
                连线
              </el-button>
              <el-button size="small" icon="el-icon-delete" @click="deleteSelected" :disabled="!selectedNode && !selectedEdge">
                删除
              </el-button>
            </el-button-group>

            <el-divider direction="vertical"></el-divider>

            <el-button-group>
              <el-button size="small" icon="el-icon-zoom-in" @click="zoomIn">放大</el-button>
              <el-button size="small" icon="el-icon-zoom-out" @click="zoomOut">缩小</el-button>
              <el-button size="small" icon="el-icon-refresh-left" @click="resetView">重置</el-button>
            </el-button-group>
          </div>

          <div class="toolbar-right">
            <el-button type="primary" size="small" icon="el-icon-check" @click="saveProcess" :loading="saving">
              保存流程
            </el-button>
          </div>
        </div>
      </el-card>
    </div>

    <div class="designer-content">
      <el-card shadow="never" class="left-panel">
        <div slot="header" class="panel-header">
          <span>节点列表 ({{ nodes.length }})</span>
        </div>
        <el-table
          :data="nodes"
          size="mini"
          highlight-current-row
          @current-change="handleNodeSelect"
          style="width: 100%">
          <el-table-column prop="nodeName" label="节点名称" min-width="90" show-overflow-tooltip>
            <template slot-scope="scope">
              <i :class="getNodeIcon(scope.row.nodeType)" style="margin-right: 5px;"></i>
              {{ scope.row.nodeName }}
            </template>
          </el-table-column>
          <el-table-column prop="nodeType" label="类型" width="60" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.nodeType === 'startEvent'" type="success" size="mini">开始</el-tag>
              <el-tag v-else-if="scope.row.nodeType === 'userTask'" type="primary" size="mini">审批</el-tag>
              <el-tag v-else-if="scope.row.nodeType === 'endEvent'" type="danger" size="mini">结束</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card shadow="never" class="canvas-panel">
        <div
          class="canvas-wrapper"
          ref="canvasWrapper"
          @mousedown="canvasMouseDown">

          <div
            class="canvas"
            :style="{
              transform: `scale(${zoom}) translate(${translateX}px, ${translateY}px)`,
              transformOrigin: '0 0'
            }">

            <div class="grid-background"></div>

            <svg class="edges-layer" width="3000" height="2000" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; pointer-events: none; z-index: 1;">
              <defs>
                <marker id="arrowhead" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
                  <polygon points="0 0, 10 3.5, 0 7" fill="#409EFF" />
                </marker>
              </defs>

              <g v-if="alignmentLines.length > 0" class="alignment-lines-group">
                <line
                  v-for="(line, index) in alignmentLines"
                  :key="'align-' + index"
                  :x1="line.x1"
                  :y1="line.y1"
                  :x2="line.x2"
                  :y2="line.y2"
                  stroke="#FF6B35"
                  stroke-width="2"
                  stroke-dasharray="8,4"
                  opacity="0.8"/>
              </g>

              <g v-for="edge in edges" :key="edge.tempId">
                <path
                  :d="getEdgePath(edge)"
                  stroke="#409EFF"
                  stroke-width="2"
                  fill="none"
                  marker-end="url(#arrowhead)"
                  @click="selectEdge(edge)"
                  class="edge-line"/>

                <text
                  v-if="edge.conditionText"
                  :x="getEdgeCenterX(edge)"
                  :y="getEdgeCenterY(edge)"
                  fill="#606266"
                  font-size="12"
                  text-anchor="middle">
                  {{ edge.conditionText }}
                </text>
              </g>

              <path
                v-if="connecting"
                :d="getTempEdgePath()"
                stroke="#409EFF"
                stroke-width="2"
                stroke-dasharray="5,5"
                fill="none"
                marker-end="url(#arrowhead)"/>
            </svg>
            <div
              v-for="node in nodes"
              :key="node.tempId"
              :class="['process-node', 'node-' + node.nodeType, {
                'node-selected': selectedNode && selectedNode.tempId === node.tempId,
                'node-connecting': connecting && connectingSourceNode && connectingSourceNode.tempId === node.tempId,
                'dragging': draggingNode && draggingNode.tempId === node.tempId
              }]"
              :style="{
                left: node.nodeX + 'px',
                top: node.nodeY + 'px'
              }"
              @mousedown.stop="nodeMouseDown($event, node)"
              @click.stop="handleNodeClick(node)">

              <div class="node-content">
                <div class="node-icon" :class="node.nodeType + '-icon'">
                  <i :class="getNodeIcon(node.nodeType)"></i>
                </div>
                <div class="node-label">{{ node.nodeName }}</div>
                <div class="node-sublabel" v-if="node.assigneeType">{{ getAssigneeText(node.assigneeType) }}</div>
              </div>

              <div class="node-delete" @click.stop="deleteNode(node)" title="删除节点">
                <i class="el-icon-close"></i>
              </div>

              <div class="connect-point connect-point-top"
                   @mousedown.stop="handleConnectPointDown(node, 'top')"
                   @mouseup.stop="handleConnectPointUp(node, 'top')"
                   title="从顶部连接"></div>
              <div class="connect-point connect-point-right"
                   @mousedown.stop="handleConnectPointDown(node, 'right')"
                   @mouseup.stop="handleConnectPointUp(node, 'right')"
                   title="从右侧连接"></div>
              <div class="connect-point connect-point-bottom"
                   @mousedown.stop="handleConnectPointDown(node, 'bottom')"
                   @mouseup.stop="handleConnectPointUp(node, 'bottom')"
                   title="从底部连接"></div>
              <div class="connect-point connect-point-left"
                   @mousedown.stop="handleConnectPointDown(node, 'left')"
                   @mouseup.stop="handleConnectPointUp(node, 'left')"
                   title="从左侧连接"></div>
            </div>
          </div>
        </div>
      </el-card>

      <el-card shadow="never" class="right-panel">
        <div slot="header" class="panel-header">
          <span>属性配置</span>
        </div>

        <div class="property-section">
          <div class="section-title">流程信息</div>
          <el-form :model="processForm" label-width="80px" size="small">
            <el-form-item label="流程标识">
              <el-input v-model="processForm.processKey" placeholder="例如: drawing_approval"></el-input>
            </el-form-item>
            <el-form-item label="流程名称">
              <el-input v-model="processForm.processName" placeholder="例如: 图纸审批流程"></el-input>
            </el-form-item>
            <el-form-item label="流程分类">
              <el-select v-model="processForm.processCategory" placeholder="请选择" style="width: 100%">
                <el-option label="图纸审批" value="drawing"></el-option>
                <el-option label="文档审批" value="document"></el-option>
                <el-option label="变更通知" value="ecn"></el-option>
                <el-option label="其他" value="other"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="流程描述">
              <el-input v-model="processForm.description" type="textarea" :rows="2"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <el-divider></el-divider>

        <div class="property-section" v-if="selectedNode">
          <div class="section-title">节点属性</div>
          <el-form label-width="80px" size="small">
            <el-form-item label="节点名称">
              <el-input v-model="selectedNode.nodeName"></el-input>
            </el-form-item>
            <el-form-item label="节点类型">
              <el-input :value="getNodeTypeText(selectedNode.nodeType)" disabled></el-input>
            </el-form-item>

            <template v-if="selectedNode.nodeType === 'userTask'">
              <el-form-item label="审批人类型">
                <el-select v-model="selectedNode.assigneeType" style="width: 100%" @change="handleAssigneeTypeChange">
                  <el-option label="指定用户" value="user"></el-option>
                  <el-option label="角色" value="role"></el-option>
                  <el-option label="部门" value="dept"></el-option>
                  <el-option label="部门负责人" value="leader"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="审批人" v-if="selectedNode.assigneeType === 'user'">
                <el-select
                  v-model="selectedNode.assigneeValue"
                  placeholder="请选择用户"
                  filterable
                  style="width: 100%">
                  <el-option
                    v-for="user in userList"
                    :key="user.userId"
                    :label="user.nickName + '(' + user.userName + ')'"
                    :value="user.userId.toString()">
                    <span style="float: left">{{ user.nickName }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ user.userName }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="审批角色" v-else-if="selectedNode.assigneeType === 'role'">
                <el-select
                  v-model="selectedNode.assigneeValue"
                  placeholder="请选择角色"
                  filterable
                  style="width: 100%">
                  <el-option
                    v-for="role in roleList"
                    :key="role.roleId"
                    :label="role.roleName"
                    :value="role.roleId.toString()">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="审批部门" v-else-if="selectedNode.assigneeType === 'dept'">
                <el-select
                  v-model="selectedNode.assigneeValue"
                  placeholder="请选择部门"
                  filterable
                  style="width: 100%">
                  <el-option
                    v-for="dept in deptList"
                    :key="dept.deptId"
                    :label="dept.deptName"
                    :value="dept.deptId.toString()">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="提示" v-else-if="selectedNode.assigneeType === 'leader'">
                <el-alert
                  title="将自动使用该部门的负责人作为审批人"
                  type="info"
                  :closable="false"
                  show-icon>
                </el-alert>
              </el-form-item>
              <el-form-item label="表单标识">
                <el-input v-model="selectedNode.formKey" placeholder="例如: drawing_form"></el-input>
              </el-form-item>
            </template>

            <el-form-item label="节点备注">
              <el-input v-model="selectedNode.remark" type="textarea" :rows="2"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <div class="property-section" v-if="selectedEdge">
          <div class="section-title">连线属性</div>
          <el-form label-width="80px" size="small">
            <el-form-item label="条件文本">
              <el-input v-model="selectedEdge.conditionText" placeholder="例如: 通过"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <div v-if="!selectedNode && !selectedEdge" class="empty-state">
          <i class="el-icon-info"></i>
          <p>选择节点或连线<br/>查看和编辑属性</p>
        </div>

        <el-divider></el-divider>

        <div class="property-section">
          <div class="section-title">统计信息</div>
          <el-descriptions :column="1" size="mini" border>
            <el-descriptions-item label="节点数量">{{ nodes.length }}</el-descriptions-item>
            <el-descriptions-item label="连线数量">{{ edges.length }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { getProcessDefinition, saveProcessDesign } from "@/api/system/process"
import { listUser } from "@/api/system/user"
import { listRole } from "@/api/system/role"
import { deptTreeSelect } from "@/api/system/user"

export default {
  name: "ProcessDesigner",
  data() {
    return {
      processId: null,
      saving: false,

      processForm: {
        id: null,
        processKey: '',
        processName: '',
        processCategory: '',
        processType: '',
        description: '',
        status: '0'
      },

      nodes: [],
      edges: [],
      selectedNode: null,
      selectedEdge: null,

      nodeCounter: 0,
      edgeCounter: 0,

      zoom: 1,
      translateX: 0,
      translateY: 0,

      draggingNode: null,
      dragOffset: { x: 0, y: 0 },
      isDraggingCanvas: false,
      canvasStartPos: { x: 0, y: 0 },

      connecting: false,
      connectingStart: { x: 0, y: 0 },
      connectingEnd: { x: 0, y: 0 },
      connectingSourceNode: null,
      connectingSourceDirection: null,
      isConnectingPoint: false,

      alignmentLines: [],
      alignmentThreshold: 5,

      userList: [],
      roleList: [],
      deptList: []
    }
  },

  created() {
    console.log('流程设计器页面已加载')
    const id = this.$route.query.id
    console.log('流程ID:', id)
    if (id && id !== 'null') {
      this.processId = id
      this.loadProcess(id)
    }

    document.addEventListener('keydown', this.canvasKeyDown)
    document.addEventListener('mousemove', this.canvasMouseMove)
    document.addEventListener('mouseup', this.canvasMouseUp)

    this.loadUserList()
    this.loadRoleList()
    this.loadDeptList()
  },

  beforeDestroy() {
    document.removeEventListener('keydown', this.canvasKeyDown)
    document.removeEventListener('mousemove', this.canvasMouseMove)
    document.removeEventListener('mouseup', this.canvasMouseUp)
  },

  methods: {
    goBack() {
      this.$router.back()
    },

    loadUserList() {
      listUser({ pageSize: 1000 }).then(response => {
        this.userList = response.rows || []
      })
    },

    loadRoleList() {
      listRole({ pageSize: 1000 }).then(response => {
        this.roleList = response.rows || []
      })
    },

    loadDeptList() {
      deptTreeSelect().then(response => {
        this.deptList = this.flattenDeptTree(response.data || [])
      })
    },

    flattenDeptTree(tree) {
      const result = []
      const traverse = (nodes) => {
        nodes.forEach(node => {
          result.push({
            deptId: node.id,
            deptName: node.label
          })
          if (node.children && node.children.length > 0) {
            traverse(node.children)
          }
        })
      }
      traverse(tree)
      return result
    },

    handleAssigneeTypeChange(value) {
      if (this.selectedNode) {
        this.selectedNode.assigneeValue = null
      }
    },

    loadProcess(id) {
      getProcessDefinition(id).then(response => {
        const data = response.data
        this.processForm = {
          id: data.id,
          processKey: data.processKey,
          processName: data.processName,
          processCategory: data.processCategory || '',
          processType: data.processType || '',
          description: data.description || '',
          status: data.status || '0'
        }

        this.nodes = (data.nodes || []).map((node, index) => ({
          ...node,
          tempId: node.id ? 'node_' + node.id : 'node_new_' + index
        }))

        this.edges = (data.edges || []).map((edge, index) => ({
          ...edge,
          tempId: edge.id ? 'edge_' + edge.id : 'edge_new_' + index
        }))

        this.nodeCounter = this.nodes.length
        this.edgeCounter = this.edges.length
      })
    },

    addNode(nodeType) {
      this.nodeCounter++
      const nodeTypeNames = {
        'startEvent': '开始',
        'userTask': '审批',
        'endEvent': '结束'
      }

      const newNode = {
        tempId: 'node_' + Date.now(),
        nodeKey: `${nodeType}_${this.nodeCounter}`,
        nodeName: nodeTypeNames[nodeType] + this.nodeCounter,
        nodeType: nodeType,
        nodeX: 100 + Math.floor(Math.random() * 500),
        nodeY: 100 + Math.floor(Math.random() * 300),
        assigneeType: nodeType === 'userTask' ? 'user' : null,
        assigneeValue: null,
        formKey: null,
        conditionExpr: null,
        remark: null,
        sort: this.nodes.length
      }

      this.nodes.push(newNode)
      this.$message.success('已添加' + nodeTypeNames[nodeType] + '节点')
    },

    deleteNode(node) {
      this.$confirm('确认删除该节点吗？关联的连线也会被删除', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const nodeId = node.tempId
        this.nodes = this.nodes.filter(n => n.tempId !== nodeId)
        this.edges = this.edges.filter(e => e.sourceNodeId !== nodeId && e.targetNodeId !== nodeId)

        if (this.selectedNode && this.selectedNode.tempId === nodeId) {
          this.selectedNode = null
        }

        this.$message.success('删除成功')
      })
    },

    deleteEdge(edge) {
      this.$confirm('确认删除该连线吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.edges = this.edges.filter(e => e.tempId !== edge.tempId)
        this.selectedEdge = null
        this.$message.success('删除成功')
      })
    },

    deleteSelected() {
      if (this.selectedNode) {
        this.deleteNode(this.selectedNode)
      } else if (this.selectedEdge) {
        this.deleteEdge(this.selectedEdge)
      }
    },

    selectNode(node) {
      this.selectedNode = node
      this.selectedEdge = null
    },

    selectEdge(edge) {
      this.selectedEdge = edge
      this.selectedNode = null
    },

    handleNodeSelect(node) {
      if (node) {
        this.selectNode(node)
      }
    },

    handleNodeClick(node) {
      if (this.isConnectingPoint && this.connectingSourceNode) {
        return
      }
      if (this.connecting && this.connectingSourceNode) {
        this.completeConnect(node)
      } else {
        this.selectNode(node)
      }
    },

    handleConnectPointDown(node, direction) {
      this.isConnectingPoint = true
      this.startConnectFromNode(node, direction)
    },

    handleConnectPointUp(node, direction) {
      if (this.connecting && this.connectingSourceNode &&
        this.connectingSourceNode.tempId !== node.tempId) {
        this.completeConnect(node, direction)
      }
      setTimeout(() => {
        this.isConnectingPoint = false
      }, 50)
    },

    getNodeIcon(nodeType) {
      const icons = {
        'startEvent': 'el-icon-circle-check',
        'userTask': 'el-icon-user-solid',
        'endEvent': 'el-icon-circle-close'
      }
      return icons[nodeType] || 'el-icon-info'
    },

    getNodeTypeText(nodeType) {
      const texts = {
        'startEvent': '开始节点',
        'userTask': '审批节点',
        'endEvent': '结束节点'
      }
      return texts[nodeType] || nodeType
    },

    getAssigneeText(assigneeType) {
      const texts = {
        'user': '指定用户',
        'role': '角色',
        'dept': '部门',
        'leader': '部门负责人'
      }
      return texts[assigneeType] || ''
    },

    getNodeConnectPoint(node, direction) {
      if (!node) return { x: 0, y: 0 }

      const nodeWidth = 120
      const nodeHeight = 70

      switch(direction) {
        case 'top':
          return {
            x: node.nodeX + nodeWidth / 2,
            y: node.nodeY
          }
        case 'right':
          return {
            x: node.nodeX + nodeWidth,
            y: node.nodeY + nodeHeight / 2
          }
        case 'bottom':
          return {
            x: node.nodeX + nodeWidth / 2,
            y: node.nodeY + nodeHeight
          }
        case 'left':
          return {
            x: node.nodeX,
            y: node.nodeY + nodeHeight / 2
          }
        default:
          return {
            x: node.nodeX + nodeWidth / 2,
            y: node.nodeY + nodeHeight / 2
          }
      }
    },

    getEdgePath(edge) {
      const sourceNode = this.nodes.find(n => n.tempId === edge.sourceNodeId)
      const targetNode = this.nodes.find(n => n.tempId === edge.targetNodeId)

      if (!sourceNode || !targetNode) return ''

      const sourceDirection = edge.sourceDirection || 'right'
      const targetDirection = edge.targetDirection || 'left'

      const startPoint = this.getNodeConnectPoint(sourceNode, sourceDirection)
      const endPoint = this.getNodeConnectPoint(targetNode, targetDirection)

      return this.calculateBezierPath(startPoint, endPoint, sourceDirection, targetDirection)
    },

    calculateBezierPath(startPoint, endPoint, sourceDir, targetDir) {
      const dx = Math.abs(endPoint.x - startPoint.x)
      const dy = Math.abs(endPoint.y - startPoint.y)

      const offset = Math.max(dx * 0.5, dy * 0.5, 50)

      let controlPoint1, controlPoint2

      switch(sourceDir) {
        case 'right':
          controlPoint1 = { x: startPoint.x + offset, y: startPoint.y }
          break
        case 'left':
          controlPoint1 = { x: startPoint.x - offset, y: startPoint.y }
          break
        case 'top':
          controlPoint1 = { x: startPoint.x, y: startPoint.y - offset }
          break
        case 'bottom':
          controlPoint1 = { x: startPoint.x, y: startPoint.y + offset }
          break
      }

      switch(targetDir) {
        case 'right':
          controlPoint2 = { x: endPoint.x + offset, y: endPoint.y }
          break
        case 'left':
          controlPoint2 = { x: endPoint.x - offset, y: endPoint.y }
          break
        case 'top':
          controlPoint2 = { x: endPoint.x, y: endPoint.y - offset }
          break
        case 'bottom':
          controlPoint2 = { x: endPoint.x, y: endPoint.y + offset }
          break
      }

      return `M ${startPoint.x} ${startPoint.y} C ${controlPoint1.x} ${controlPoint1.y}, ${controlPoint2.x} ${controlPoint2.y}, ${endPoint.x} ${endPoint.y}`
    },

    getEdgeCenterX(edge) {
      const sourceNode = this.nodes.find(n => n.tempId === edge.sourceNodeId)
      const targetNode = this.nodes.find(n => n.tempId === edge.targetNodeId)

      if (!sourceNode || !targetNode) return 0

      const sourceDirection = edge.sourceDirection || 'right'
      const targetDirection = edge.targetDirection || 'left'

      const startPoint = this.getNodeConnectPoint(sourceNode, sourceDirection)
      const endPoint = this.getNodeConnectPoint(targetNode, targetDirection)

      return (startPoint.x + endPoint.x) / 2
    },

    getEdgeCenterY(edge) {
      const sourceNode = this.nodes.find(n => n.tempId === edge.sourceNodeId)
      const targetNode = this.nodes.find(n => n.tempId === edge.targetNodeId)

      if (!sourceNode || !targetNode) return 0

      const sourceDirection = edge.sourceDirection || 'right'
      const targetDirection = edge.targetDirection || 'left'

      const startPoint = this.getNodeConnectPoint(sourceNode, sourceDirection)
      const endPoint = this.getNodeConnectPoint(targetNode, targetDirection)

      return (startPoint.y + endPoint.y) / 2 - 8
    },

    getTempEdgePath() {
      if (!this.connectingSourceNode || !this.connectingSourceDirection) return ''

      const startPoint = this.connectingStart
      const endPoint = this.connectingEnd

      const dx = Math.abs(endPoint.x - startPoint.x)
      const dy = Math.abs(endPoint.y - startPoint.y)

      const offset = Math.max(dx * 0.5, dy * 0.5, 50)

      let controlPoint1, controlPoint2

      switch(this.connectingSourceDirection) {
        case 'right':
          controlPoint1 = { x: startPoint.x + offset, y: startPoint.y }
          break
        case 'left':
          controlPoint1 = { x: startPoint.x - offset, y: startPoint.y }
          break
        case 'top':
          controlPoint1 = { x: startPoint.x, y: startPoint.y - offset }
          break
        case 'bottom':
          controlPoint1 = { x: startPoint.x, y: startPoint.y + offset }
          break
      }

      controlPoint2 = { x: endPoint.x, y: endPoint.y }

      return `M ${startPoint.x} ${startPoint.y} C ${controlPoint1.x} ${controlPoint1.y}, ${controlPoint2.x} ${controlPoint2.y}, ${endPoint.x} ${endPoint.y}`
    },

    nodeMouseDown(e, node) {
      e.stopPropagation()
      e.preventDefault()

      if (this.connecting) {
        return
      }

      this.draggingNode = node

      const wrapper = this.$refs.canvasWrapper
      const rect = wrapper.getBoundingClientRect()

      this.dragOffset = {
        x: (e.clientX - rect.left - this.translateX) / this.zoom - node.nodeX,
        y: (e.clientY - rect.top - this.translateY) / this.zoom - node.nodeY
      }

      if (this.draggingNode.$el) {
        this.draggingNode.$el.classList.add('dragging')
      }

      document.addEventListener('mousemove', this.nodeDragMove, { passive: false })
      document.addEventListener('mouseup', this.nodeDragEnd)
    },

    nodeDragMove(e) {
      if (!this.draggingNode) return
      e.preventDefault()

      const wrapper = this.$refs.canvasWrapper
      if (!wrapper) return

      const rect = wrapper.getBoundingClientRect()
      const x = Math.max(0, Math.round((e.clientX - rect.left - this.translateX) / this.zoom - this.dragOffset.x))
      const y = Math.max(0, Math.round((e.clientY - rect.top - this.translateY) / this.zoom - this.dragOffset.y))

      this.draggingNode.nodeX = x
      this.draggingNode.nodeY = y

      if (this.draggingNode.$el) {
        this.draggingNode.$el.style.left = x + 'px'
        this.draggingNode.$el.style.top = y + 'px'
      }

      this.updateAlignmentLines()
    },

    updateAlignmentLines() {
      if (!this.draggingNode) {
        this.alignmentLines = []
        return
      }

      const lines = []
      const draggingNode = this.draggingNode
      const nodeWidth = 120
      const nodeHeight = 70

      const draggingCenterX = draggingNode.nodeX + nodeWidth / 2
      const draggingCenterY = draggingNode.nodeY + nodeHeight / 2

      let hasVerticalAlignment = false
      let hasHorizontalAlignment = false

      for (let node of this.nodes) {
        if (node.tempId === draggingNode.tempId) continue

        const nodeCenterX = node.nodeX + nodeWidth / 2
        const nodeCenterY = node.nodeY + nodeHeight / 2

        if (!hasVerticalAlignment && Math.abs(draggingCenterX - nodeCenterX) < this.alignmentThreshold) {
          lines.push({
            x1: nodeCenterX,
            y1: 0,
            x2: nodeCenterX,
            y2: 2000
          })
          draggingNode.nodeX = node.nodeX
          if (this.draggingNode.$el) {
            this.draggingNode.$el.style.left = node.nodeX + 'px'
          }
          hasVerticalAlignment = true
        }

        if (!hasHorizontalAlignment && Math.abs(draggingCenterY - nodeCenterY) < this.alignmentThreshold) {
          lines.push({
            x1: 0,
            y1: nodeCenterY,
            x2: 3000,
            y2: nodeCenterY
          })
          draggingNode.nodeY = node.nodeY
          if (this.draggingNode.$el) {
            this.draggingNode.$el.style.top = node.nodeY + 'px'
          }
          hasHorizontalAlignment = true
        }

        if (hasVerticalAlignment && hasHorizontalAlignment) {
          break
        }
      }

      this.alignmentLines = lines
    },


    nodeDragEnd() {
      if (this.draggingNode && this.draggingNode.$el) {
        this.draggingNode.$el.classList.remove('dragging')
      }

      this.alignmentLines = []

      document.removeEventListener('mousemove', this.nodeDragMove)
      document.removeEventListener('mouseup', this.nodeDragEnd)
      this.draggingNode = null
    },

    canvasMouseDown(e) {
      if (this.connecting) {
        return
      }

      if (e.target.classList.contains('canvas-wrapper') ||
        e.target.classList.contains('grid-background') ||
        e.target.tagName === 'svg') {
        e.preventDefault()
        this.isDraggingCanvas = true
        this.canvasStartPos = {
          x: e.clientX - this.translateX,
          y: e.clientY - this.translateY
        }

        document.addEventListener('mousemove', this.canvasDragMove, { passive: false })
        document.addEventListener('mouseup', this.canvasDragEnd)
      }
    },

    canvasDragMove(e) {
      if (!this.isDraggingCanvas) return
      e.preventDefault()

      this.translateX = e.clientX - this.canvasStartPos.x
      this.translateY = e.clientY - this.canvasStartPos.y
    },

    canvasDragEnd() {
      document.removeEventListener('mousemove', this.canvasDragMove)
      document.removeEventListener('mouseup', this.canvasDragEnd)
      this.isDraggingCanvas = false
    },

    canvasMouseMove(e) {
      if (this.connecting) {
        const wrapper = this.$refs.canvasWrapper
        const rect = wrapper.getBoundingClientRect()
        this.connectingEnd = {
          x: (e.clientX - rect.left - this.translateX) / this.zoom,
          y: (e.clientY - rect.top - this.translateY) / this.zoom
        }
      }
    },

    canvasMouseUp(e) {
      if (this.connecting && this.isConnectingPoint) {
        const targetNode = this.getTargetNodeFromPosition(
          this.connectingEnd.x,
          this.connectingEnd.y
        )
        if (targetNode && targetNode.tempId !== this.connectingSourceNode.tempId) {
          const direction = this.getDirectionFromPosition(targetNode, this.connectingEnd)
          this.completeConnect(targetNode, direction)
        } else {
          this.cancelConnect()
        }
      }
    },

    getTargetNodeFromPosition(x, y) {
      for (let node of this.nodes) {
        if (x >= node.nodeX && x <= node.nodeX + 120 &&
          y >= node.nodeY && y <= node.nodeY + 70) {
          return node
        }
      }
      return null
    },

    getDirectionFromPosition(node, position) {
      const centerX = node.nodeX + 60
      const centerY = node.nodeY + 35
      const dx = position.x - centerX
      const dy = position.y - centerY

      if (Math.abs(dx) > Math.abs(dy)) {
        return dx > 0 ? 'right' : 'left'
      } else {
        return dy > 0 ? 'bottom' : 'top'
      }
    },

    canvasKeyDown(e) {
      if (e.key === 'Escape' && this.connecting) {
        this.cancelConnect()
        this.$message.info('已取消连线')
      }
    },

    zoomIn() {
      this.zoom = Math.min(this.zoom + 0.1, 2)
    },

    zoomOut() {
      this.zoom = Math.max(this.zoom - 0.1, 0.5)
    },

    resetView() {
      this.zoom = 1
      this.translateX = 0
      this.translateY = 0
    },

    startConnect() {
      if (!this.selectedNode) {
        this.$message.warning('请先选择一个源节点')
        return
      }
      this.isConnectingPoint = false
      this.connecting = true
      this.connectingSourceNode = this.selectedNode
      this.connectingSourceDirection = 'right'

      const startPoint = this.getNodeConnectPoint(this.selectedNode, 'right')
      this.connectingStart = { x: startPoint.x, y: startPoint.y }
      this.connectingEnd = { ...this.connectingStart }

      this.$message.info('请点击目标节点完成连线，或按ESC取消')
    },

    startConnectFromNode(node, direction) {
      this.connecting = true
      this.connectingSourceNode = node
      this.connectingSourceDirection = direction

      const startPoint = this.getNodeConnectPoint(node, direction)
      this.connectingStart = { x: startPoint.x, y: startPoint.y }
      this.connectingEnd = { ...this.connectingStart }
    },

    completeConnect(targetNode, targetDirection = 'left') {
      if (!this.connectingSourceNode || !targetNode) return

      const sourceId = this.connectingSourceNode.tempId
      const targetId = targetNode.tempId

      if (sourceId === targetId) {
        this.$message.warning('不能连接到自身')
        this.cancelConnect()
        return
      }

      const exists = this.edges.some(e =>
        e.sourceNodeId === sourceId &&
        e.targetNodeId === targetId &&
        e.sourceDirection === this.connectingSourceDirection &&
        e.targetDirection === targetDirection
      )

      if (exists) {
        this.$message.warning('该连线已存在')
        this.cancelConnect()
        return
      }

      this.edgeCounter++
      const newEdge = {
        tempId: 'edge_' + Date.now(),
        edgeKey: 'flow_' + this.edgeCounter,
        sourceNodeId: sourceId,
        sourceDirection: this.connectingSourceDirection,
        targetNodeId: targetId,
        targetDirection: targetDirection,
        conditionText: null,
        conditionExpr: null,
        sort: this.edges.length
      }

      this.edges.push(newEdge)
      this.cancelConnect()
      this.$message.success('连线成功')
    },

    cancelConnect() {
      this.connecting = false
      this.connectingSourceNode = null
      this.connectingSourceDirection = null
      this.connectingStart = { x: 0, y: 0 }
      this.connectingEnd = { x: 0, y: 0 }
    },

    saveProcess() {
      if (!this.processForm.processKey) {
        this.$message.error('请输入流程标识')
        return
      }
      if (!this.processForm.processName) {
        this.$message.error('请输入流程名称')
        return
      }
      if (this.nodes.length === 0) {
        this.$message.error('请至少添加一个节点')
        return
      }

      this.saving = true

      const params = {
        definition: this.processForm,
        nodes: this.nodes.map(node => ({
          id: node.id || null,
          nodeKey: node.nodeKey,
          nodeName: node.nodeName,
          nodeType: node.nodeType,
          nodeX: node.nodeX,
          nodeY: node.nodeY,
          assigneeType: node.assigneeType,
          assigneeValue: node.assigneeValue,
          formKey: node.formKey,
          conditionExpr: node.conditionExpr,
          remark: node.remark,
          sort: node.sort
        })),
        edges: this.edges.map(edge => ({
          id: edge.id || null,
          edgeKey: edge.edgeKey,
          sourceNodeId: edge.sourceNodeId,
          sourceDirection: edge.sourceDirection || 'right',
          targetNodeId: edge.targetNodeId,
          targetDirection: edge.targetDirection || 'left',
          conditionText: edge.conditionText,
          conditionExpr: edge.conditionExpr,
          sort: edge.sort
        }))
      }
      saveProcessDesign(params).then(response => {
        this.$modal.msgSuccess('保存成功')
        this.saving = false
        setTimeout(() => {
          this.goBack()
        }, 500)
      }).catch(() => {
        this.saving = false
      })
    }
  }
}
</script>

<style scoped>
.process-designer {
  height: calc(100vh - 84px);
  display: flex;
  flex-direction: column;
}

.designer-header {
  margin-bottom: 10px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.designer-content {
  flex: 1;
  display: flex;
  gap: 10px;
  overflow: hidden;
}

.left-panel, .right-panel {
  width: 280px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.left-panel >>> .el-card__body,
.right-panel >>> .el-card__body {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
}

.canvas-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.canvas-panel >>> .el-card__body {
  flex: 1;
  padding: 0;
  overflow: hidden;
}

.canvas-wrapper {
  width: 100%;
  height: 100%;
  overflow: hidden;
  background: #f5f7fa;
  cursor: grab;
  position: relative;
}

.canvas-wrapper:active {
  cursor: grabbing;
}

.canvas {
  width: 3000px;
  height: 2000px;
  position: relative;
  background: #fff;
}

.grid-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image:
    linear-gradient(rgba(0, 0, 0, 0.05) 1px, transparent 1px),
    linear-gradient(90deg, rgba(0, 0, 0, 0.05) 1px, transparent 1px);
  background-size: 20px 20px;
  pointer-events: none;
}

.edges-layer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1;
}

.alignment-lines {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 3;
}

.edge-line {
  pointer-events: stroke;
  cursor: pointer;
}

.edge-line:hover {
  stroke-width: 3;
  stroke: #67C23A;
}

.process-node {
  position: absolute;
  min-width: 120px;
  padding: 10px;
  background: #fff;
  border: 2px solid #DCDFE6;
  border-radius: 8px;
  cursor: move;
  user-select: none;
  z-index: 2;
  text-align: center;
  transform: translate3d(0, 0, 0);
  backface-visibility: hidden;
  perspective: 1000px;
}

.process-node:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border-color: #409EFF;
}

.process-node.node-selected {
  border-color: #409EFF;
  box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.2);
}

.process-node.node-connecting {
  border-color: #67C23A;
  box-shadow: 0 0 0 3px rgba(103, 194, 58, 0.3);
  animation: pulse 1.5s infinite;
}

@keyframes pulse {
  0%, 100% {
    box-shadow: 0 0 0 3px rgba(103, 194, 58, 0.3);
  }
  50% {
    box-shadow: 0 0 0 6px rgba(103, 194, 58, 0.1);
  }
}

.process-node.dragging {
  opacity: 0.9;
  cursor: grabbing;
  z-index: 100;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
  transition: none !important;
}

.connect-point {
  position: absolute;
  width: 10px;
  height: 10px;
  background: #409EFF;
  border: 2px solid #fff;
  border-radius: 50%;
  cursor: crosshair;
  opacity: 0;
  transition: opacity 0.2s, transform 0.2s, background 0.2s;
  z-index: 10;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.process-node:hover .connect-point {
  opacity: 1;
}

.connect-point:hover {
  transform: scale(1.4);
  background: #67C23A;
}

.connect-point-top {
  left: 50%;
  top: -5px;
  transform: translateX(-50%);
}

.connect-point-top:hover {
  transform: translateX(-50%) scale(1.4);
}

.connect-point-right {
  right: -5px;
  top: 50%;
  transform: translateY(-50%);
}

.connect-point-right:hover {
  transform: translateY(-50%) scale(1.4);
}

.connect-point-bottom {
  left: 50%;
  bottom: -5px;
  transform: translateX(-50%);
}

.connect-point-bottom:hover {
  transform: translateX(-50%) scale(1.4);
}

.connect-point-left {
  left: -5px;
  top: 50%;
  transform: translateY(-50%);
}

.connect-point-left:hover {
  transform: translateY(-50%) scale(1.4);
}

.node-icon {
  width: 40px;
  height: 40px;
  margin: 0 auto 8px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: #fff;
}

.startEvent-icon {
  background: #67C23A;
}

.userTask-icon {
  background: #409EFF;
}

.endEvent-icon {
  background: #F56C6C;
}

.node-label {
  font-size: 13px;
  color: #303133;
  font-weight: 500;
  margin-bottom: 4px;
}

.node-sublabel {
  font-size: 11px;
  color: #909399;
}

.node-delete {
  position: absolute;
  top: -8px;
  right: -8px;
  width: 20px;
  height: 20px;
  background: #F56C6C;
  color: #fff;
  border-radius: 50%;
  display: none;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 12px;
  z-index: 3;
}

.process-node:hover .node-delete {
  display: flex;
}

.panel-header {
  font-weight: bold;
  font-size: 14px;
}

.property-section {
  margin-bottom: 10px;
}

.section-title {
  font-weight: bold;
  font-size: 13px;
  color: #303133;
  margin-bottom: 10px;
  padding-left: 8px;
  border-left: 3px solid #409EFF;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}

.empty-state i {
  font-size: 48px;
  display: block;
  margin-bottom: 10px;
}
</style>

